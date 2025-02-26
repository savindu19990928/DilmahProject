package com.dilmah.hostel.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

@Service
public class JasperReportService {

    @Autowired
    private DataSource dataSource;

    public byte[] generateReport(String reportFileName, Map<String, Object> parameters) throws Exception {
        // Load the JRXML template from resources folder
    	InputStream reportStream = getClass().getClassLoader().getResourceAsStream("reports/" + reportFileName + ".jrxml");

        // Compile the JRXML template to JasperReport
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Get database connection
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            // Fill the report with parameters and database connection
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Export report to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
