package com.dilmah.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dilmah.hostel.service.JasperReportService;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class JasperReportController {

	@Autowired
	private JasperReportService jasperReportService;

	@GetMapping("/{reportName}")
	public ResponseEntity<byte[]> generateReport(@PathVariable String reportName,
			@RequestParam Map<String, Object> parameters) {
		try {
			// Generate the report PDF
			byte[] pdfReport = jasperReportService.generateReport(reportName, parameters);

			// Set headers for file download
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=" + reportName + ".pdf");
			headers.add("Content-Type", "application/pdf");

			return new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
