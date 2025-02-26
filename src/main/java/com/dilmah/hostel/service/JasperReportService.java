package com.dilmah.hostel.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class JasperReportService {

	@Autowired
	private DataSource dataSource;

	public byte[] generateReport(String reportFileName, Map<String, Object> parameters) throws Exception {
		// Load the compiled Jasper file
		try (InputStream reportStream = getClass().getClassLoader()
				.getResourceAsStream("reports/" + reportFileName + ".jasper")) {

			if (reportStream == null) {
				throw new FileNotFoundException("Report file not found: reports/" + reportFileName + ".jasper");
			}

			// Load the compiled JasperReport object
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

			// Get database connection
			try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
				// Fill the report with parameters and database connection
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

				// Export report to PDF
				return JasperExportManager.exportReportToPdf(jasperPrint);
			}
		} catch (JRException | SQLException e) {
			// Log the exception if a logger is available
			throw new Exception("Error generating the report: " + e.getMessage(), e);
		}
	}

}
