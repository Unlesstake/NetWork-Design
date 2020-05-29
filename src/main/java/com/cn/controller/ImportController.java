package com.cn.controller;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cn.dao.RecordDao;
import com.cn.dao.SalesDao;
import com.cn.entity.ImportData;
import com.cn.entity.Sales;
import com.cn.entity.SalesRecord;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
public class ImportController {

    @Resource
    RecordDao recorddao;

    @Resource
    SalesDao salesdao;

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (cell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (cell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                    Double d = cell.getNumericCellValue();

                    DecimalFormat df = new DecimalFormat("#.##");//数字格式例如1.11
                    String value = df.format(d);
                    cellValue = value;
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = null;
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    @PostMapping("/import")
    @ResponseBody
    public String readExcelData(@RequestBody ImportData importData) throws IOException {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        String cellData = null;
        String filePath = importData.getFilepath();
        if (filePath == null) {
            return null;
        }

        try {
            boolean status = filePath.contains(".");
            if (!status){
                JSONObject object = new JSONObject();
                object.put("code","400");
                object.put("desc","导入失败！文件类型错误！");
                return object.toString();
            }
            String extString = filePath.substring(filePath.lastIndexOf("."));
            InputStream is = null;
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)||".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
                List<SalesRecord> list = new ArrayList<SalesRecord>();
                //获取第一个sheet
                sheet = wb.getSheetAt(0);
                //获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();
                //获取第一行
                row = sheet.getRow(0);
                //获取最大列数
//                int colnum = row.getPhysicalNumberOfCells();
                for (int i = 1; i<rownum; i++) {
                    row = sheet.getRow(i);
                    SalesRecord salesRecord = new SalesRecord();
                    for (int j=0;j<8;j++){
                        cellData = getCellValue(row.getCell(j));
                        if (cellData!="") {
                            if (j == 0) {
                                salesRecord.setStoreName(cellData);
                            } else if (j == 1) {
                                salesRecord.setGoodsName(cellData);
                            } else if (j == 2) {
                                salesRecord.setPrice(Double.parseDouble(cellData));
                            } else if (j == 3) {
                                salesRecord.setUnit(cellData);
                            } else if (j == 4) {
                                salesRecord.setNumber(Integer.parseInt(cellData));
                            } else if (j == 5) {
                                salesRecord.setTotalPrice(Double.parseDouble(cellData));
                            } else if (j == 6) {
                                salesRecord.setBuyerAge(Integer.parseInt(cellData));
                            } else {
                                salesRecord.setDealTime(cellData);
                                salesRecord.setStoreId(importData.getId());
                                list.add(salesRecord);
                            }
                        }else {
                            break;
                        }
                    }
                }
                if (list.size()==0){
                    JSONObject object = new JSONObject();
                    object.put("code","400");
                    object.put("desc","导入失败！导入文件没有数据！");
                    return object.toString();
                }
                int flag = recorddao.add(list);
                if(flag>0) {
                    List<SalesRecord> RecordList = recorddao.find(importData.getId());
                    int under_age = 0;
                    int puber = 0;
                    int young_man = 0;
                    int middle_aged = 0;
                    int aged = 0;
                    for (int i = 0; i < RecordList.size(); i++) {
                        int age = RecordList.get(i).getBuyerAge();
                        int number = RecordList.get(i).getNumber();
                        if (age < 18) {
                            under_age = under_age + number;
                        } else if (age >= 18 && age <= 28) {
                            puber = puber + number;
                        } else if (age >= 29 && age <= 40) {
                            young_man = young_man + number;
                        } else if (age >= 41 && age <= 65) {
                            middle_aged = middle_aged + number;
                        } else {
                            aged = aged + number;
                        }
                    }
                    Sales sales = new Sales();
                    sales.setId(importData.getId());
                    sales.setUnder_age(under_age);
                    sales.setPuber(puber);
                    sales.setYoung_man(young_man);
                    sales.setMiddle_aged(middle_aged);
                    sales.setAged(aged);
                    int flag2 = salesdao.update(sales);
                }else{
                    JSONObject object = new JSONObject();
                    object.put("code","400");
                    object.put("desc","导入失败！请检查数据格式！");
                    return object.toString();
                }

            }else {
                wb = null;
                JSONObject object = new JSONObject();
                object.put("code","400");
                object.put("desc","导入失败！文件类型不符合规范！");
                return object.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JSONObject object = new JSONObject();
            object.put("code","500");
            object.put("desc","导入失败！请检查文件路径与格式！");
            return object.toString();
        } catch (IOException e) {
            e.printStackTrace();
            JSONObject object = new JSONObject();
            object.put("code","500");
            object.put("desc","导入失败！请检查文件路径与格式！");
            return object.toString();
        }
        JSONObject object = new JSONObject();
        object.put("code","200");
        object.put("desc","导入成功！");;
        return object.toString();

    }

}