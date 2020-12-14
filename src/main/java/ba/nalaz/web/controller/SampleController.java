package ba.nalaz.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ba.nalaz.AppConstants;
import ba.nalaz.model.core.Lab;
import ba.nalaz.model.core.ProductConstants;
import ba.nalaz.model.core.Role;
import ba.nalaz.model.core.Sample;
import ba.nalaz.model.core.User;
import ba.nalaz.service.AccessControlManager;
import ba.nalaz.service.SampleManager;
import ba.nalaz.service.UserManager;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;
import ba.nalaz.web.helper.ResourceForbiddenException;
import ba.nalaz.web.validation.SampleValidator;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

@Controller
@RequestMapping(value = "/sample")
public class SampleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);
	
    private static Font regularFont = new Font(Font.FontFamily.HELVETICA, 12);
    private static Font onlyBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    public static final String FONT = "./src/main/resources/font/arial.ttf";

	@Autowired
	private AccessControlManager accessControlManager;
	@Autowired
	private SampleManager sampleManager;
	@Autowired
	private SampleValidator sampleValidator;
	@Autowired
	private UserManager userManager;

	@InitBinder("sample")
	private void initBinder(WebDataBinder binder) {
		LOGGER.info("binder[{}]", new Object[] { binder });
		try {
			binder.setValidator(sampleValidator);
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView createSample(final HttpServletRequest request) {
		LOGGER.info("createSample");
		ModelAndView mav = new ModelAndView("sample/sampleCreate");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkLabNonEmpty(user)) {
				throw new ResourceForbiddenException();
			}
			Sample sample = new Sample();
			mav.addObject("sample", sample);
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView createSample(@ModelAttribute @Valid Sample sample, final BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request) {
		LOGGER.info("sample[{}]bindingResult[{}]", new Object[] { sample, bindingResult });
		ModelAndView mav = null;
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			Lab lab = (Lab) request.getAttribute(AppConstants.LAB_OBJ);
			if (!accessControlManager.checkLabNonEmpty(user)) {
				throw new ResourceForbiddenException();
			}
			if (bindingResult.hasErrors()) {
				mav = new ModelAndView("sample/sampleCreate");
				return mav;
			}
			sample.setCreatedUser(user);
			sample.setCreatedDate(new Date());
			sample.setModifiedUser(user);
			sample.setModifiedDate(new Date());
			sample.setCompletedDate(null);
			sample.setLab(lab);
			sample.setDeleted(false);
			sample.setSampleCode(user.getSubLab() + "0" + user.getId() + "20");
			sample.setOverallStatus(0);
			mav = new ModelAndView("redirect:/sample/list/" + lab.getId());
			sampleManager.saveSample(sample);
			mav = new ModelAndView("redirect:/sample/barcode/view/" + sample.getId());
			redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Uzorak je uspješno kreiran.");
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView getSampleList(@PathVariable("id") Long labId, final HttpServletRequest request) {
		LOGGER.info("labId[{}]", new Object[] { labId });
		ModelAndView mav = new ModelAndView("sample/sampleList");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkUserLabVsLab(user, labId)) {
				throw new ResourceForbiddenException();
			}
			PartialList partialList = null;
			Pagination pagination = new Pagination(request);
			if (user.getUserType().getId() == 3) {
				partialList = sampleManager.getSampleListAsCompanyUser(pagination, labId, user.getId());
			} else {
				partialList = sampleManager.getSampleList(pagination, labId);
			}
			mav.addObject("search", pagination.getQuery());
			mav.addObject("total", partialList.getSize());
			mav.addObject("data", partialList.getList());
			// if user is company admin and his search results have one result equal to barcode than redirect to that sample
			if (user.getUserType().getId() == 2 && partialList.getSize() == 1) {
				Sample sampleFromSearch = (Sample)partialList.getList().get(0);
				if (pagination.getQuery()!=null && pagination.getQuery().equals(sampleFromSearch.getSampleCode())) {
					mav = new ModelAndView("redirect:/sample/edit/" + sampleFromSearch.getId());
				}
			}
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView editSample(@PathVariable("id") Long id, final HttpServletRequest request) {
		LOGGER.info("id[{}]", new Object[] { id });
		ModelAndView mav = new ModelAndView("sample/sampleEdit");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkEditSampleAccess(user, id)) {
				throw new ResourceForbiddenException();
			}
			Set<Role> roles = user.getRoles();
			Role adminKompanijeRole = userManager.getRole("ROLE_ADMIN_KOMPANIJE");
			if (roles.contains(adminKompanijeRole)) {
				mav = new ModelAndView("sample/sampleResult");
			}
			Sample sample = sampleManager.getSample(id);
			mav.addObject("sampleCode", sample.getSampleCode());
			mav.addObject("sample", sample);
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView editSample(@ModelAttribute @Valid Sample sample, final BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request) {
		LOGGER.info("sample[{}]bindingResult[{}]", new Object[] { sample, bindingResult });
		ModelAndView mav = null;
		try {
			Lab lab = (Lab) request.getAttribute(AppConstants.LAB_OBJ);
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			Sample labSample = sampleManager.getSample(sample.getId());
			if (!accessControlManager.checkEditSampleAccess(user, sample.getId())) {
				throw new ResourceForbiddenException();
			}
			if (bindingResult.hasErrors()) {
				sample.setSampleCode(labSample.getSampleCode());
				return new ModelAndView("sample/sampleEdit");
			}
			labSample.setModifiedDate(new Date());
			labSample.setModifiedUser(user);
			labSample.setLab(lab);
			labSample.setDeleted(false);
			labSample.setPatientName(sample.getPatientName());
			labSample.setPatientSurname(sample.getPatientSurname());
			labSample.setBirthDate(sample.getBirthDate());
			labSample.setGender(sample.getGender());
			labSample.setAddress(sample.getAddress());
			labSample.setCity(sample.getCity());
			labSample.setPhoneNumber(sample.getPhoneNumber());
			labSample.setEmail(sample.getEmail());

			labSample.setSampleType(sample.getSampleType());
			labSample.setAnalysisType(sample.getAnalysisType());
			labSample.setMethod(sample.getMethod());
			labSample.setAnalysisReason(sample.getAnalysisReason());
			labSample.setNote(sample.getNote());

			Set<Role> roles = user.getRoles();
			Role adminKompanijeRole = userManager.getRole("ROLE_ADMIN_KOMPANIJE");
			if (roles.contains(adminKompanijeRole)) {
				labSample.seteGenResult(sample.geteGenResult());
				labSample.setnGenResult(sample.getnGenResult());
				labSample.setOverallResult(sample.getOverallResult());
				labSample.setAnalysisPurpose(sample.getAnalysisPurpose());

				if (labSample.getOverallResult() != null && labSample.getOverallResult() != "") {
					labSample.setOverallStatus(1);
					if (labSample.getCompletedDate() == null) {
						labSample.setCompletedDate(new Date());
					}
				}
			}
			sampleManager.saveSample(labSample);
			redirectAttributes.addFlashAttribute(ProductConstants.MESSAGE_SUCCESS, "Uzorak je uspješno spašen.");
			if(user.getUserType().getId()==2 && labSample.getOverallStatus()==1) {
				mav = new ModelAndView("redirect:/sample/print/" + labSample.getId());
			} else {
				mav = new ModelAndView("redirect:/sample/list/" + lab.getId());
			}							
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView deleteSample(@RequestParam("selectedId") Long id, final HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		LOGGER.info("id[{}]", new Object[] { id });
		ModelAndView mav = null;
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			Lab org = (Lab) request.getAttribute(AppConstants.LAB_OBJ);
			mav = new ModelAndView("redirect:/sample/list/" + org.getId());
			Sample sample = sampleManager.getSample(id);
			if (!accessControlManager.checkUserLabVsLab(user, sample.getLab().getId())) {
				throw new ResourceForbiddenException();
			}
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping("/search")
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelMap searchSample(final HttpServletRequest request, @RequestParam("q") String q) {
		LOGGER.info("q[{}]", new Object[] { q });
		ModelMap modelMap = new ModelMap();
		try {
			Lab org = (Lab) request.getAttribute(AppConstants.LAB_OBJ);
			List<Sample> list = sampleManager.searchSample(q, org.getId());
			modelMap.addAttribute("total", list.size());
			modelMap.addAttribute("result", list);
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return modelMap;
	}

	@RequestMapping(value = "/barcode/view/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView viewBarcode(@PathVariable("id") Long id, final HttpServletRequest request) {
		LOGGER.info("viewBarcode(Long, HttpServletRequest)");
		ModelAndView mav = new ModelAndView("barcode/barcodeView");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkLabNonEmpty(user)) {
				throw new ResourceForbiddenException();
			}
			Sample sample = sampleManager.getSample(id);
			sample.setImage(BlobProxy.generateProxy(getImage(sample)));
			sampleManager.saveSample(sample);
			Blob image = sample.getImage();
			byte[] imgData = image.getBytes(1l, (int) image.length());
			String encode = Base64.getEncoder().encodeToString(imgData);
			request.setAttribute("imgBase", encode);
			mav.addObject("sample", sample);
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/print/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView printSample(@PathVariable("id") Long id, final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		LOGGER.info("printSample(Long, HttpServletRequest)");
		ModelAndView mav = new ModelAndView("sample/samplePrint");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkLabNonEmpty(user)) {
				throw new ResourceForbiddenException();
			}
			Sample sample = sampleManager.getSample(id);
			if (!accessControlManager.checkUserVsSampleCreated(user,sample.getCreatedUser().getId())) {
				throw new ResourceForbiddenException();
			}
			if(sample.getOverallStatus()!=1) {
				throw new ResourceForbiddenException();
			}
			mav.addObject("sample", sample);
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}

	public static byte[] getImage(Sample sample) throws BarcodeException, OutputException {
		Path path = Paths.get("nalaz-barcodes");
		String pathString = path.toString();
		boolean directoryExists = Files.exists(path);
		if (!directoryExists) {
			new File(pathString).mkdirs();
		}
		Barcode barcode = BarcodeFactory.createCode128(sample.getSampleCode());
		barcode.setBarHeight(120);
		barcode.setBarWidth(3);
		File file = new File(path + "\\" + sample.getSampleCode() + ProductConstants.BARCODE_PICTURE_TYPE);
		if (!file.exists()) {
			BarcodeImageHandler.savePNG(barcode, file);
		}
		if (file.exists()) {
			try {
				BufferedImage bufferedImage = ImageIO.read(file);
				ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", byteOutStream);
				return byteOutStream.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/processedList/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public ModelAndView getSampleProcessedList(@PathVariable("id") Long labId, final HttpServletRequest request) {
		LOGGER.info("labId[{}]", new Object[] { labId });
		ModelAndView mav = new ModelAndView("sample/sampleProcessedList");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkUserLabVsLab(user, labId)) {
				throw new ResourceForbiddenException();
			}
			PartialList partialList = null;
			Pagination pagination = new Pagination(request);
			if (user.getUserType().getId() == 3) {
				partialList = sampleManager.getSampleProcessedListAsCompanyUser(pagination, labId, user.getId());
			} else {
				partialList = sampleManager.getSampleProcessedList(pagination, labId);
			}
			mav.addObject("search", pagination.getQuery());
			mav.addObject("total", partialList.getSize());
			mav.addObject("data", partialList.getList());
			// if user is company admin and his search results have one result equal to barcode than redirect to that sample
			if (user.getUserType().getId() == 2 && partialList.getSize() == 1) {
				Sample sampleFromSearch = (Sample)partialList.getList().get(0);
				if (pagination.getQuery()!=null && pagination.getQuery().equals(sampleFromSearch.getSampleCode())) {
					mav = new ModelAndView("redirect:/sample/edit/" + sampleFromSearch.getId());
				}
			}
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
		return mav;
	}
	
	@RequestMapping(value = "/downloadbarcode/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public void downloadbarcode(@PathVariable("id") Long id, final HttpServletRequest request, final HttpServletResponse response ) {
		LOGGER.info("downloadbarcode(Long, HttpServletRequest)");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkLabNonEmpty(user)) {
				throw new ResourceForbiddenException();
			}
			Sample sample = sampleManager.getSample(id);
			Blob image = sample.getImage();
			byte[] imgData = image.getBytes(1l, (int) image.length());
			response.setContentType("image/png");
			String attachmentName = "attachment; filename=" + sample.getPatientName() + " " + sample.getPatientSurname() + " " + sample.getSampleCode() + ".png";
			response.setHeader("Content-Disposition", attachmentName);
	        response.getOutputStream().write(imgData);
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
	}
	
	@RequestMapping(value = "/downloadresult/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN_KOMPANIJE','ROLE_KORISNIK_KOMPANIJE','ROLE_KORISNIK')")
	public void downloadresult(@PathVariable("id") Long id, final HttpServletRequest request, final HttpServletResponse response ) {
		LOGGER.info("downloadresult(Long, HttpServletRequest)");
		try {
			User user = (User) request.getAttribute(AppConstants.USER_OBJ);
			if (!accessControlManager.checkLabNonEmpty(user)) {
				throw new ResourceForbiddenException();
			}
			Sample sample = sampleManager.getSample(id);
				

			Document document = new Document();
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfWriter.getInstance(document, baos);
	        document.open();       
	        
	        addContent(document, sample);
	        
	        document.close();
	        HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = sample.getPatientName()  + " " + sample.getPatientSurname() + " " + sample.getSampleCode() + ".pdf";
			headers.add("content-disposition", "inline;filename=" + filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	        OutputStream os = response.getOutputStream();
	        baos.writeTo(os);
	        os.flush();
		} catch (ResourceForbiddenException e) {
			LOGGER.warn(ProductConstants.ACCESS_DENIED);
			throw e;
		} catch (Exception e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED, e);
		}
	}

    private void addContent(Document document, Sample sample) throws DocumentException, IOException {

        Paragraph preface = new Paragraph();
        preface.add(new Paragraph(ProductConstants.ALEA_NAME));
        preface.add(new Paragraph(ProductConstants.ALEA_ADDRESS));
        preface.add(new Paragraph(ProductConstants.ALEA_ID));
        preface.add(new Paragraph(ProductConstants.ALEA_PHONE_NUMBER + ProductConstants.ALEA_MOBILE_PHONE_NUMBER));
        preface.add(new Paragraph(ProductConstants.ALEA_EMAIL));
        addEmptyLine(preface, 1);
        document.add(preface);
        BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource("img/ALEA-logo.jpg"));
        Image image1 = Image.getInstance(image, null);
        createTable(document, image1);
        createTable2(document, sample);        
        createTable3(document, sample);
        addFooter(document);
    }

    private static void createTable(Document document, Image image)
            throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell c1 = new PdfPCell(image);
        c1.setBorderColor(BaseColor.WHITE);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(ProductConstants.ALEA_CERTIFICATE));
        c1.setBorderColor(BaseColor.WHITE);
        c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(c1);
        document.add(table);
    }
    
    private static void createTable2(Document document, Sample sample) throws DocumentException {
       	Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        document.add(preface);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        //first row
        Phrase p1 = new Phrase();
        p1.add(new Chunk("Ime i prezime: ", onlyBold));
        p1.add(new Chunk(sample.getPatientName() + " " + sample.getPatientSurname(), regularFont));
        PdfPCell c1 = new PdfPCell(p1);
        c1.setBorderColor(BaseColor.WHITE);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Broj protokola:", onlyBold));
        c1.setBorderColor(BaseColor.WHITE);
        c1.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(c1);
        //second row
        Phrase p2 = new Phrase ();
        p2.add(new Chunk("Vrijeme uzimanja brisa: ", onlyBold));
        p2.add(new Chunk(dateToString(sample.getCreatedDate()), regularFont));        
        PdfPCell c2 = new PdfPCell(p2);
        c2.setBorderColor(BaseColor.WHITE);
        table.addCell(c2);
        c2 = new PdfPCell(new Phrase(""));
        c2.setBorderColor(BaseColor.WHITE);
        c2.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(c2);
        //third row
        Phrase p3 = new Phrase ();
        p3.add(new Chunk("Datum izdavanja nalaza: ", onlyBold));
        p3.add(new Chunk(dateToString(sample.getCompletedDate()), regularFont));
        PdfPCell c3 = new PdfPCell(p3);
        c3.setBorderColor(BaseColor.WHITE);
        table.addCell(c3);
        Phrase p4 = new Phrase ();
        p4.add(new Chunk("Tip uzorka: ", onlyBold));
        p4.add(new Chunk(sample.getSampleType(), regularFont));
        c3 = new PdfPCell(p4);
        c3.setBorderColor(BaseColor.WHITE);
        c3.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(c3);
        //fourth row
        PdfPCell c4 = new PdfPCell(new Phrase("\n"));
        c4.setBorderColor(BaseColor.WHITE);
        table.addCell(c4);
        c4 = new PdfPCell(new Phrase("\n"));
        c4.setBorderColor(BaseColor.WHITE);
        table.addCell(c4);
        //fifth row
        PdfPCell c5 = new PdfPCell(new Phrase("\n"));
        c5.setBorderColor(BaseColor.WHITE);
        table.addCell(c5);
        c5 = new PdfPCell(new Phrase("\n"));
        c5.setBorderColor(BaseColor.WHITE);
        table.addCell(c5);
        //sixth row
        Phrase p5 = new Phrase ();
        p5.add(new Chunk("Vrsta analize: ", onlyBold));
        p5.add(new Chunk(sample.getAnalysisType(), regularFont));
        PdfPCell c6 = new PdfPCell(p5);
        c6.setBorderColor(BaseColor.WHITE);
        table.addCell(c6);
        c6 = new PdfPCell(new Phrase(""));
        c6.setBorderColor(BaseColor.WHITE);
        table.addCell(c6);
        //seventh row
        Phrase p6 = new Phrase ();
        p6.add(new Chunk("Metod rada: ", onlyBold));
        p6.add(new Chunk(sample.getMethod(), regularFont));
        PdfPCell c7 = new PdfPCell(p6);
        c7.setBorderColor(BaseColor.WHITE);
        table.addCell(c7);
        c7 = new PdfPCell(new Phrase(""));
        c7.setBorderColor(BaseColor.WHITE);
        table.addCell(c7);
        //eighth row
        Phrase p7 = new Phrase ();
        p7.add(new Chunk("Cilj analize: ", onlyBold));
        PdfPCell c8 = new PdfPCell(p7);
        c8.setBorderColor(BaseColor.WHITE);
        table.addCell(c8);
        c8 = new PdfPCell(new Phrase(""));
        c8.setBorderColor(BaseColor.WHITE);
        table.addCell(c8);
        //ninth row
        PdfPCell c9 = new PdfPCell(new Phrase("\n"));
        c9.setBorderColor(BaseColor.WHITE);
        table.addCell(c9);
        c9 = new PdfPCell(new Phrase("\n"));
        c9.setBorderColor(BaseColor.WHITE);
        table.addCell(c9);
        document.add(table);
        
        PdfPTable newTable = new PdfPTable(1);
        newTable.setWidthPercentage(100);
        PdfPCell cell1 = new PdfPCell(new Phrase(sample.getAnalysisPurpose()));
        cell1.setBorderColor(BaseColor.WHITE);
        newTable.addCell(cell1);
        document.add(newTable);
        
    }
    
    private static void createTable3(Document document, Sample sample) throws DocumentException {
    	Paragraph preface1 = new Paragraph();
        addEmptyLine(preface1, 1);
        document.add(preface1);
    	PdfPTable newTable1 = new PdfPTable(1);
        newTable1.setWidthPercentage(100);
        PdfPCell cell1 = new PdfPCell(new Phrase("Analiza:", onlyBold));
        cell1.setBorderColor(BaseColor.WHITE);
        newTable1.addCell(cell1);
        document.add(newTable1);
    	
    	Paragraph preface2 = new Paragraph();
        addEmptyLine(preface2, 1);
        document.add(preface2);
        PdfPTable table2 = new PdfPTable(2);
        table2.setWidthPercentage(60);
        //first row
        PdfPCell c1 = new PdfPCell(new Phrase("Target regija: "));
        c1.setUseVariableBorders(true);
        c1.setBorderColorLeft(BaseColor.WHITE);
        c1.setBorderColorRight(BaseColor.WHITE);
        table2.addCell(c1);
        c1 = new PdfPCell(new Phrase("Nalaz:"));
        c1.setUseVariableBorders(true);
        c1.setBorderColorLeft(BaseColor.WHITE);
        c1.setBorderColorRight(BaseColor.WHITE);        
        table2.addCell(c1);
        //second row
        PdfPCell c2 = new PdfPCell(new Phrase("E gen: "));
        c2.setUseVariableBorders(true);
        c2.setBorderColorLeft(BaseColor.WHITE);
        c2.setBorderColorRight(BaseColor.WHITE);
        table2.addCell(c2);
        c2 = new PdfPCell(new Phrase(sample.geteGenResult(), onlyBold));
        c2.setUseVariableBorders(true);
        c2.setBorderColorLeft(BaseColor.WHITE);
        c2.setBorderColorRight(BaseColor.WHITE);
        table2.addCell(c2);
        //third row
        PdfPCell c3 = new PdfPCell(new Phrase("N gen:"));
        c3.setUseVariableBorders(true);
        c3.setBorderColorLeft(BaseColor.WHITE);
        c3.setBorderColorRight(BaseColor.WHITE);
        table2.addCell(c3);
        c3 = new PdfPCell(new Phrase(sample.getnGenResult(), onlyBold));
        c3.setUseVariableBorders(true);
        c3.setBorderColorLeft(BaseColor.WHITE);
        c3.setBorderColorRight(BaseColor.WHITE);
        table2.addCell(c3);
        table2.setHorizontalAlignment(Element.ALIGN_LEFT);
        document.add(table2);
        
    	Paragraph preface3 = new Paragraph();
        addEmptyLine(preface3, 1);
        document.add(preface3);
        
        PdfPTable table3 = new PdfPTable(1);
        table3.setWidthPercentage(100);
        PdfPCell celltable1 = new PdfPCell(new Phrase("Rezultat:", onlyBold));
        celltable1.setBorderColor(BaseColor.WHITE);
        table3.addCell(celltable1);
        PdfPCell celltable2 = new PdfPCell(new Phrase("\n"));
        celltable2.setBorderColor(BaseColor.WHITE);
        table3.addCell(celltable2);
        PdfPCell celltable3 = new PdfPCell(new Phrase(sample.getOverallResult().toUpperCase(), onlyBold));
        celltable3.setBorderColor(BaseColor.WHITE);
        table3.addCell(celltable3);
        document.add(table3);
        
    }
    
    private void addFooter(Document document) throws DocumentException {
    	String path = getClass().getClassLoader().getResource("font/arial.ttf").toString();
        FontFactory.register(path);
        Font textFont = FontFactory.getFont("arial", BaseFont.IDENTITY_H, 
        	    BaseFont.EMBEDDED, 12);
    	PdfPTable kera = new PdfPTable(1);
        kera.setWidthPercentage(100);
    	PdfPCell cell = new PdfPCell(new Phrase("Analizirao:\n\n_____________________________\nDr. Sci. Rijad Konjhodžić", textFont));
    	cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    	cell.setBorderColor(BaseColor.WHITE);
    	kera.addCell(cell);
    	kera.setExtendLastRow(true);
    	document.add(kera);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    public static String dateToString(Date date){
        if(date != null) {
            Date utilDate = new Date(date.getTime());
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. hh:mm", Locale.ENGLISH);
            return dateFormat.format(utilDate);
        }
        return null;
    }
}
