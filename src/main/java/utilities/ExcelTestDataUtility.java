package utilities;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class ExcelTestDataUtility {


        public String path;
        public FileInputStream fis = null;
        public FileOutputStream fileOut = null;
        private XSSFWorkbook workbook = null;
        private XSSFSheet sheet = null;
        private XSSFRow row = null;
        private XSSFCell cell = null;

        public ExcelTestDataUtility(String path) {

            this.path = path;
            try {
                fis = new FileInputStream(path);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // returns the row count in a sheet

        /**
         * Code has been updated as per new POI version - 4.x.x
         * @param sheetName
         *@param rowNum
         * @param colNum
         * @return
         */
        // returns the data from a cell
        public String getCellData(String sheetName, int rowNum, int colNum) {
            try {
                if (rowNum <= 0)
                    return "";
                int index = workbook.getSheetIndex(sheetName);
                if (index == -1)
                    return "";

                sheet = workbook.getSheetAt(index);
                row = sheet.getRow(rowNum - 1);
                if (row == null)
                    return "";
                cell = row.getCell(colNum - 1);
                if (cell == null)
                    return "";

                //
                if (cell.getCellType().name().equals("STRING"))
                    return cell.getStringCellValue();

                    //
                    // if (cell.getCellType().STRING != null)
                    // return cell.getStringCellValue();
                else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

                    String cellText = String.valueOf(cell.getNumericCellValue());
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // format in form of M/D/YY
                        double d = cell.getNumericCellValue();

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(HSSFDateUtil.getJavaDate(d));
                        cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                        cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

                        // System.out.println(cellText);

                    }

                    return cellText;
                } else if (cell.getCellType().BLANK != null)
                    return "";
                else
                    return String.valueOf(cell.getBooleanCellValue());
            } catch (Exception e) {

                e.printStackTrace();
                return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
            }
        }



    }

