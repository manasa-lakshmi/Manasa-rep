package ceix.com.bc.pojo;

import com.ceix.common.util.SFTPConnectionUtil;

import com.ceix.common.util.SFTPCredentials;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.MalformedURLException;

import java.text.ParseException;

import oracle.jbo.RowSetIterator;


import com.itextpdf.text.pdf.PdfPageEvent;

import oracle.jbo.ViewObject;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import com.jcraft.jsch.SftpException;

import com.tangosol.coherence.dsltools.termtrees.Terms;

import java.awt.Color;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.MalformedURLException;

import java.text.ParseException;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.jws.WebMethod;
import javax.jws.WebService;

import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.client.Configuration;

import oracle.jbo.server.ViewRowImpl;

import sun.font.FontFamily;


public class ShippingNotification extends PdfPageEventHelper {

    private static final ADFLogger _logger = ADFLogger.createADFLogger(ShippingNotification.class);
    private static Font font12b = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font font10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    private static Font font9 = new Font(Font.FontFamily.TIMES_ROMAN, 9);
    PdfTemplate total;

    private static Font helvetic10b = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static Font times8pt = new Font(Font.FontFamily.TIMES_ROMAN, 8);

    private static Font helvetica13b = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
    private static Font helvetica11b = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
    private int totalItems = 0;
    private double totalTons = 0;

    public ShippingNotification() {
        super();
    }

    public PdfPCell getCellBorderDetails(String value, int alignment, Font font) {
        PdfPCell cell = new PdfPCell();


        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setVerticalAlignment(1);
        cell.setPaddingRight(5);
        cell.setPaddingLeft(5);
        cell.setBorderColorBottom(BaseColor.BLACK);
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(alignment);

        cell.addElement(p);

        return cell;
    }

    public PdfPCell getHeaderStyle(String value, int alignment, Font font) {
        PdfPCell cell = new PdfPCell();

        Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
        f1.setColor(BaseColor.WHITE);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setVerticalAlignment(1);
        cell.setBorderColorBottom(BaseColor.WHITE);
        cell.setBackgroundColor(BaseColor.BLACK);

        cell.setPaddingRight(5);
        cell.setPaddingLeft(5);
        cell.setBorder(PdfPCell.NO_BORDER);
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(alignment);
        p.setFont(f1);
        cell.addElement(p);

        return cell;
    }

    public PdfPCell getSubHeaderStyle(String value, int alignment, Font font) {
        PdfPCell cell = new PdfPCell();

        Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
        f1.setColor(BaseColor.BLACK);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setVerticalAlignment(1);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        cell.setPaddingRight(5);
        cell.setPaddingLeft(5);
        //cell.setBorder(PdfPCell.NO_BORDER);
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(alignment);
        p.setFont(f1);
        cell.addElement(p);

        return cell;
    }


    public PdfPTable createStandardHeader(ViewObject header) throws BadElementException, MalformedURLException, IOException,


                                                                    DocumentException {

        PdfPTable MainWrapperTable;
        MainWrapperTable = new PdfPTable(1);
        MainWrapperTable.setWidthPercentage(100);
        MainWrapperTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        MainWrapperTable.getDefaultCell().setBorderColor(BaseColor.BLACK);
        MainWrapperTable.getDefaultCell().setVerticalAlignment(0);
        MainWrapperTable.getDefaultCell().setBorderWidthBottom(1.5f);
        MainWrapperTable.setKeepTogether(false);
        //MainWrapperTable.getDefaultCell().setBorder(Rectangle.TOP|Rectangle.LEFT|Rectangle.RIGHT );


        // Header Record
        PdfPTable table_1 = new PdfPTable(1);
        table_1.getDefaultCell().setPaddingTop(0);
        table_1.setWidthPercentage(100);
        table_1.setKeepTogether(false);
        table_1.addCell(getHeaderStyle("SHIPPING CONFIRMATION", Element.ALIGN_CENTER, helvetica13b));
        MainWrapperTable.addCell(table_1);


        RowSetIterator iter = header.createRowSetIterator(null);


        while (iter.hasNext()) {
            Row row = iter.next();
            //Header of Shipper and Consignee
            PdfPTable table_head1 = new PdfPTable(2);
            table_head1.getDefaultCell().setPaddingTop(0);
            table_head1.setWidthPercentage(100);
            table_head1.setKeepTogether(false);
            table_head1.addCell(getSubHeaderStyle("SHIPPER", Element.ALIGN_CENTER, helvetica11b));
            table_head1.addCell(getSubHeaderStyle("CONSIGNEE", Element.ALIGN_CENTER, helvetica11b));
            MainWrapperTable.addCell(table_head1);


            //Shipper and Consignee records
            PdfPTable table_2 = new PdfPTable(2);
            table_2.getDefaultCell().setPaddingTop(0);
            table_2.setWidthPercentage(100);
            table_2.setKeepTogether(false);

            String shipper = "";
            if (row.getAttribute("Shipper") != null)
                shipper = row.getAttribute("Shipper").toString();

            String shipperAddress = "";
            if (row.getAttribute("ShipperAddress") != null)
                shipperAddress = row.getAttribute("ShipperAddress").toString();

            String shipperCityStateZip = "";
            if (row.getAttribute("ShipperCityStateZip") != null)
                shipperCityStateZip = row.getAttribute("ShipperCityStateZip").toString();

            String consignee = "";
            if (row.getAttribute("Consignee") != null)
                consignee = row.getAttribute("Consignee").toString();

            String consigneeAddress = "";
            if (row.getAttribute("ConsigneeAddress") != null)
                consigneeAddress = row.getAttribute("ConsigneeAddress").toString();

            String consigneeCityStateZip = "";
            if (row.getAttribute("ConsigneeCityStateZip") != null)
                consigneeCityStateZip = row.getAttribute("ConsigneeCityStateZip").toString();

            String mine = "";
            if (row.getAttribute("Mine") != null)
                mine = row.getAttribute("Mine").toString();

            String shipperCell = "NAME: " + shipper;
            String shipperAddrCell = "ADDRESS: " + shipperAddress;
            String shipperCityCell = "CITY/STATE/ZIP: " + shipperCityStateZip;

            String consigneeCell = "CONSIGNEE: " + consignee;
            String consigneeAddrCell = "CONSIGNEE ADDRESS: " + consigneeAddress;
            String consigneeCityStateZipCell = "CONSIGNEE CITY/STATE/ZIP: " + consigneeCityStateZip;

            String mineCell = "MINE: " + mine;

            table_2.addCell(getCellBorderDetails(shipperCell, Element.ALIGN_LEFT, times8pt));
            table_2.addCell(getCellBorderDetails(consigneeCell, Element.ALIGN_LEFT, times8pt));
            table_2.addCell(getCellBorderDetails(shipperAddrCell, Element.ALIGN_LEFT, times8pt));
            table_2.addCell(getCellBorderDetails(consigneeAddrCell, Element.ALIGN_LEFT, times8pt));
            table_2.addCell(getCellBorderDetails(shipperCityCell, Element.ALIGN_LEFT, times8pt));
            table_2.addCell(getCellBorderDetails(consigneeCityStateZipCell, Element.ALIGN_LEFT, times8pt));
            table_2.addCell(getCellBorderDetails(mineCell, Element.ALIGN_LEFT, times8pt));
            table_2.addCell(getCellBorderDetails("", Element.ALIGN_LEFT, times8pt));

            MainWrapperTable.addCell(table_2);

            //Header of Shipment Details and Carrier Information
            PdfPTable table_head2 = new PdfPTable(2);
            table_head2.getDefaultCell().setPaddingTop(0);
            table_head2.setWidthPercentage(100);
            table_head2.setKeepTogether(false);
            table_head2.addCell(getSubHeaderStyle("SHIPMENT DETAILS", Element.ALIGN_CENTER, helvetica11b));
            table_head2.addCell(getSubHeaderStyle("CARRIER INFORMATION", Element.ALIGN_CENTER, helvetica11b));
            MainWrapperTable.addCell(table_head2);

            //Shipment Details and Carrier Information
            PdfPTable table_3 = new PdfPTable(2);
            table_3.getDefaultCell().setPaddingTop(0);
            table_3.setWidthPercentage(100);
            table_3.setKeepTogether(false);
            String shippedDate = "";
            if (row.getAttribute("ShipDate") != null)
                shippedDate = row.getAttribute("ShipDate").toString();

            String shipperIdNumber = "";
            if (row.getAttribute("ShipperIdNumber") != null)
                shipperIdNumber = row.getAttribute("ShipperIdNumber").toString();

            String unitTrainNumber = "";
            if (row.getAttribute("UnitTrainNumber") != null)
                unitTrainNumber = row.getAttribute("UnitTrainNumber").toString();

            String rrPermitNumber = "";
            if (row.getAttribute("RrPermitNumber") != null)
                rrPermitNumber = row.getAttribute("RrPermitNumber").toString();

            String routing = "";
            if (row.getAttribute("Routing") != null)
                routing = row.getAttribute("Routing").toString();

            String class1 = "";
            if (row.getAttribute("Class1") != null)
                class1 = row.getAttribute("Class1").toString();
            
            String poNumber = "";
            if (row.getAttribute("PoNumber") != null)
                poNumber = row.getAttribute("PoNumber").toString();
            

            String salesAgreement = "";
            if (row.getAttribute("SalesAgreement") != null)
                salesAgreement = row.getAttribute("SalesAgreement").toString();

            String shippedDateCell = "SHIP DATE: " + shippedDate;
            String shipperIdNumberCell = "SHIPPER ID NO: " + shipperIdNumber;
            String poNumberCell = "CUSTOMER PO NUMBER: " + poNumber;
            String unitTrainNumberCell = "UNIT TRAIN NO: " + unitTrainNumber;
            String rrPermitNumberCell = "RR PERMIT NO: " + rrPermitNumber;
            String routingCell = "ROUTING: " + routing;
            String class1Cell = "CLASS: " + class1;
            String salesAgreementCell = "AGREEMENT NO: " + salesAgreement;

            table_3.addCell(getCellBorderDetails(shippedDateCell, Element.ALIGN_LEFT, times8pt));
            table_3.addCell(getCellBorderDetails(unitTrainNumberCell, Element.ALIGN_LEFT, times8pt));
            table_3.addCell(getCellBorderDetails(salesAgreementCell, Element.ALIGN_LEFT, times8pt));
            table_3.addCell(getCellBorderDetails(rrPermitNumberCell, Element.ALIGN_LEFT, times8pt));
            table_3.addCell(getCellBorderDetails(poNumberCell, Element.ALIGN_LEFT, times8pt));
            table_3.addCell(getCellBorderDetails("", Element.ALIGN_LEFT, times8pt));
            table_3.addCell(getCellBorderDetails(shipperIdNumberCell, Element.ALIGN_LEFT, times8pt)); 
            table_3.addCell(getCellBorderDetails("", Element.ALIGN_LEFT, times8pt));

            MainWrapperTable.addCell(table_3);

            //Header of Routing Information
            PdfPTable table_head3 = new PdfPTable(2);
            table_head3.getDefaultCell().setPaddingTop(0);
            table_head3.setWidthPercentage(100);
            table_head3.setKeepTogether(false);
            table_head3.addCell(getSubHeaderStyle("", Element.ALIGN_CENTER, helvetica11b));
            table_head3.addCell(getSubHeaderStyle("ROUTING INFORMATION", Element.ALIGN_CENTER, helvetica11b));
            MainWrapperTable.addCell(table_head3);

            //Routing Information
            PdfPTable table_4 = new PdfPTable(2);
            table_4.getDefaultCell().setPaddingTop(0);
            table_4.setWidthPercentage(100);
            table_4.setKeepTogether(false);
            table_4.addCell(getCellBorderDetails("", Element.ALIGN_LEFT, times8pt));
            table_4.addCell(getCellBorderDetails(routingCell, Element.ALIGN_LEFT, times8pt));
            table_4.addCell(getCellBorderDetails("", Element.ALIGN_LEFT, times8pt));
            table_4.addCell(getCellBorderDetails(class1Cell, Element.ALIGN_LEFT, times8pt));
            MainWrapperTable.addCell(table_4);

            _logger.log(Level.FINER, "Width" + MainWrapperTable.getTotalWidth());

            //Header of Car details
            PdfPTable table_head4 = new PdfPTable(9);
            table_head4.getDefaultCell().setPaddingTop(0);
            table_head4.setWidthPercentage(100);
            table_head4.setKeepTogether(false);
            table_head4.addCell(getSubHeaderStyle("SEQ", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("INIT", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("NUMBER", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("SEQ", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("INIT", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("NUMBER", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("SEQ", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("INIT", Element.ALIGN_CENTER, times8pt));
            table_head4.addCell(getSubHeaderStyle("NUMBER", Element.ALIGN_CENTER, times8pt));
            MainWrapperTable.addCell(table_head4);

        }
        return MainWrapperTable;
    }

    public PdfPTable createCarDetails(ViewObject lineVO) throws BadElementException, MalformedURLException, IOException,


                                                                DocumentException {

        PdfPTable MainWrapperTable;
        MainWrapperTable = new PdfPTable(1);
        MainWrapperTable.setWidthPercentage(100);
        MainWrapperTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        MainWrapperTable.getDefaultCell().setBorderColor(BaseColor.BLACK);
        //MainWrapperTable.getDefaultCell().setVerticalAlignment(0);
        //MainWrapperTable.getDefaultCell().setBorderWidthBottom(1.5f);
        MainWrapperTable.setKeepTogether(false);


        //Table Initialization for Car details
        PdfPTable table_lines1 = new PdfPTable(9);
        table_lines1.getDefaultCell().setPaddingTop(0);
        table_lines1.setWidthPercentage(50);
        table_lines1.setKeepTogether(false);

        RowSetIterator lineRowSetIter = lineVO.createRowSetIterator(null);
        lineRowSetIter.reset();
        // Add the loop for Car details here
        int linesCount = 0;
        totalTons = 0;
        while (lineRowSetIter.hasNext()) {
            linesCount = linesCount + 1;
            totalItems = totalItems + 1;

            // System.out.println("linesCount:"+linesCount+"linesToPrint:"+linesToPrint);

            Row linesRow = lineRowSetIter.next();
            //Car details
            String sequence = "";
            if (linesRow.getAttribute("Seq") != null)
                sequence = linesRow.getAttribute("Seq").toString();

            String carPrefix = "";
            if (linesRow.getAttribute("CarPrefix") != null)
                carPrefix = linesRow.getAttribute("CarPrefix").toString();

            String carId = "";
            if (linesRow.getAttribute("CarId") != null)
                carId = linesRow.getAttribute("CarId").toString();

            String weightInTons = "";
            if (linesRow.getAttribute("WeightInTons") != null)
                weightInTons = linesRow.getAttribute("WeightInTons").toString();

            totalTons = totalTons + Double.valueOf(weightInTons);


            table_lines1.addCell(getCellBorderDetails(sequence, Element.ALIGN_LEFT, times8pt));
            table_lines1.addCell(getCellBorderDetails(carPrefix, Element.ALIGN_LEFT, times8pt));
            table_lines1.addCell(getCellBorderDetails(carId, Element.ALIGN_LEFT, times8pt));

        }
        table_lines1.completeRow();
        MainWrapperTable.addCell(table_lines1);
        table_lines1.setExtendLastRow(false);
        return table_lines1;
    }

    public PdfPTable createQualityDetails(ViewObject header) throws BadElementException, MalformedURLException, IOException,


                                                                    DocumentException {
        PdfPTable MainWrapperTable;
        MainWrapperTable = new PdfPTable(1);
        MainWrapperTable.setWidthPercentage(100);
        MainWrapperTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        MainWrapperTable.getDefaultCell().setBorderColor(BaseColor.BLACK);
        MainWrapperTable.getDefaultCell().setVerticalAlignment(0);
        MainWrapperTable.getDefaultCell().setBorderWidthBottom(1.5f);
        MainWrapperTable.setKeepTogether(false);

        RowSetIterator qualRowSetIter = header.createRowSetIterator(null);
        qualRowSetIter.reset();

        System.out.println(qualRowSetIter.getFetchedRowCount());
        System.out.println(qualRowSetIter.hasNext());


        while (qualRowSetIter.hasNext()) {
            Row row = qualRowSetIter.next();

            //Footer of Car details
            PdfPTable table_CarFooter = new PdfPTable(2);
            table_CarFooter.getDefaultCell().setPaddingTop(0);
            table_CarFooter.setWidthPercentage(100);
            table_CarFooter.setKeepTogether(false);

            String loadBegan = "";
            if (row.getAttribute("LoadStartDate") != null)
                loadBegan = row.getAttribute("LoadStartDate").toString();

            String loadCompleted = "";
            if (row.getAttribute("LoadEndDate") != null)
                loadCompleted = row.getAttribute("LoadEndDate").toString();

            table_CarFooter.addCell(getCellBorderDetails("LOADING ITEMS:" + totalItems, Element.ALIGN_LEFT, times8pt));
            table_CarFooter.addCell(getCellBorderDetails("LOADING TONS:" + Math.round(totalTons*100D)/100D, Element.ALIGN_LEFT, times8pt));
            table_CarFooter.addCell(getCellBorderDetails("LOADING BEGAN:" + loadBegan, Element.ALIGN_LEFT, times8pt));
            //table_CarFooter.addCell(getCellBorderDetails("", Element.ALIGN_LEFT, times8pt));
            table_CarFooter.addCell(getCellBorderDetails("LOADING COMPLETED:" + loadCompleted, Element.ALIGN_LEFT,
                                                         times8pt));

            MainWrapperTable.addCell(table_CarFooter);

            //Header of Quality
            PdfPTable table_head5 = new PdfPTable(1);
            table_head5.getDefaultCell().setPaddingTop(0);
            table_head5.setWidthPercentage(100);
            table_head5.setKeepTogether(false);
            table_head5.addCell(getSubHeaderStyle("ANALYZER QUALITY INFORMATION", Element.ALIGN_CENTER, helvetica11b));
            MainWrapperTable.addCell(table_head5);

            String freezeConditionAmt = "";
            if (row.getAttribute("FreezeConditioner") != null)
                freezeConditionAmt = row.getAttribute("FreezeConditioner").toString();

            String sideRelease = "";
            if (row.getAttribute("SideRelease") != null)
                sideRelease = row.getAttribute("SideRelease").toString();

            String moisture = "";
            if (row.getAttribute("Moisture") != null)
                moisture = row.getAttribute("Moisture").toString();

            String ftr = "";
            if (row.getAttribute("Ftr") != null)
                ftr = row.getAttribute("Ftr").toString();

            String ash = "";
            if (row.getAttribute("Ash") != null)
                ash = row.getAttribute("Ash").toString();

            String t250 = "";
            if (row.getAttribute("T250") != null)
                t250 = row.getAttribute("T250").toString();

            String sulfur = "";
            if (row.getAttribute("Sulfur") != null)
                sulfur = row.getAttribute("Sulfur").toString();

            String str = "";
            if (row.getAttribute("Str") != null)
                str = row.getAttribute("Str").toString();

            String btu = "";
            if (row.getAttribute("Btu") != null)
                btu = row.getAttribute("Btu").toString();

            String id = "";
            if (row.getAttribute("Id") != null)
                id = row.getAttribute("Id").toString();

            String so2 = "";
            if (row.getAttribute("So2") != null)
                so2 = row.getAttribute("So2").toString();

            String h12w = "";
            if (row.getAttribute("H12w") != null)
                h12w = row.getAttribute("H12w").toString();

            String freezeConditionAmtCell = "FREEZE CONDITIONING AMOUNT: " + freezeConditionAmt;
            String sideReleaseCell = "SIDE RELEASE: " + sideRelease;
            String moistureCell = "MOISTURE: " + moisture;
            String ftrCell = "FTR:  " + ftr;
            String ashCell = "ASH: " + ash;
            String t250Cell = "T250: " + t250;
            String sulfurCell = "SULFUR: " + sulfur;
            String strCell = "STR: " + str;
            String btuCell = "BTU: " + btu;
            String idCell = "I.D: " + id;
            String so2Cell = "SO2: " + so2;
            String h12wCell = "H=1/2W: " + h12w;

            PdfPTable table_5 = new PdfPTable(2);
            table_5.getDefaultCell().setPaddingTop(0);
            table_5.setWidthPercentage(100);
            table_5.setKeepTogether(false);
            table_5.addCell(getCellBorderDetails(freezeConditionAmtCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(sideReleaseCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(moistureCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(ftrCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(ashCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(t250Cell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(sulfurCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(strCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(btuCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(idCell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(so2Cell, Element.ALIGN_LEFT, times8pt));
            table_5.addCell(getCellBorderDetails(h12wCell, Element.ALIGN_LEFT, times8pt));
            MainWrapperTable.addCell(table_5);
        }

        return MainWrapperTable;
    }

    public String createPDFInternal(ViewObject header, ViewObject line, String trainNumber) throws DocumentException,
                                                                                                   ParseException,
                                                                                                   IOException,
                                                                                                   MalformedURLException,
                                                                                                   FileNotFoundException,
                                                                                                   Exception {

        String status = "Success";


        Document document = new Document(PageSize.LETTER, 12, 12, 70, 30);
        try {

            String DEST = "CEIX_Ship_Notification_" + trainNumber + ".pdf";
            String fileWithPath = getPathWithTempDir(DEST);
            String remoteDir = "CEIX_OracleERP/INTF/Outbound/OM/SHIPNOTIFICATION/In/"+DEST;
            String archiveDir = "CEIX_OracleERP/INTF/Outbound/OM/SHIPNOTIFICATION/Archive/"+DEST;
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileWithPath));

            ShippingNotification event = new ShippingNotification();
            _logger.log(Level.FINER, "FileName " + DEST);
            writer.setPageEvent(event);

            document.open();


            document.add(createStandardHeader(header));
            document.add(createCarDetails(line));
            //document.add(createCarDetails2(header, lines,10));
            document.add(createQualityDetails(header));
            document.close();
            load(fileWithPath,remoteDir,archiveDir);
             
             
            
            return DEST;
        } catch (Exception e) {
            _logger.severe("Error while generating PDF", e);
            throw e;
        } finally {
            if (document.isOpen())
                document.close();
        }


    }


    public static String getPathWithTempDir(String inPath) {

        // return "tmp/" + inPath;
        return "/tmp/" + inPath;
    }

    public String load(String ftpFileName,String remoteFile, String ARCHIVE_DIR) {
        
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        InputStream in_stream = null;
        System.out.println("Before Initializing SFTP Connection Step1:");
        SFTPConnectionUtil connectionUtil = new SFTPConnectionUtil();
        System.out.println("Before Initializing SFTP Connection Step2:");
        com.ceix.common.util.SFTPCredentials sftpCredentials;
        System.out.println("Before Initializing SFTP Connection Step3:");
        try {
            _logger.log(Level.FINER,
                        "Opening SFTP connection and trying to read CSV File.");
 
            JSch jsch = new JSch();
            
              sftpCredentials= new SFTPCredentials();
            
            session = jsch.getSession(sftpCredentials.getSFTP_USER()
                                      , sftpCredentials.getSFTP_HOST()
                                      , sftpCredentials.getSFTP_PORT());
            session.setPassword(sftpCredentials.getSFTP_PASS());
 
            /*session = jsch.getSession("ceixdev2user"
                                                  , "129.158.84.163"
                                                  , 22);
            session.setPassword("welcome1"); 
            */
            //session.setProxy(proxy);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            //channel = session.openChannel("sftp");
            //channel.connect();
            channelSftp=(ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            System.out.println("ftpFileName:"+ftpFileName);
            System.out.println("remoteFile:"+remoteFile);
            
            //channelSftp=(ChannelSftp)channel;
            channelSftp.put(ftpFileName, remoteFile);
            System.out.println("File FTPed to: "+remoteFile);
            channelSftp.rm(ftpFileName);
            
            channelSftp.exit();
     
  
            _logger.log(Level.FINER, "File has been read successfully.");
  
        } catch (JSchException e) {
            _logger.log(Level.FINER,
                        "JSchException occurred during reading file from SFTP server due to " +
                        e.getMessage());
            System.out.println("ERROR**: "+ e.getMessage());
            

        } catch (SftpException e) {
            _logger.log(Level.FINER,
                        "SftpException occurred during reading file from SFTP server due to " +
                        e.getMessage());
            System.out.println("SftpException ERROR**: "+ e.getMessage());
             
        } catch (Exception e) {
        } finally {
            if (in_stream != null)
                try {
                    in_stream.close();
                } catch (IOException e) {
                }
            if (channel != null)
                channel.disconnect();
            if (session != null)
                session.disconnect();
            
        }
        
        return "Success";

    }


}
