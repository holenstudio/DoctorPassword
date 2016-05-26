package com.holenstudio.doctorpassword.util;

import com.holenstudio.doctorpassword.model.PasswordInfo;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Holen on 2016/5/25.
 */
public class DocUtil {

    public static File getPasswordFile() {
        return new File(FileUtil.docDir(), "password.xls");
    }
    public static File getPasswordXlsxFile() {
        return new File(FileUtil.docDir(), "password.xlsx");
    }

    public static List<PasswordInfo> getPswInfoListFromDoc() {
        File file = getPasswordFile();
        List<PasswordInfo> pswInfoList = new ArrayList<>();
//        try {
//
//
//
//
////            FileOutputStream fos = new FileOutputStream(file);
////            fs.writeFilesystem(fos);
////            fos.close();
////            HSSFWorkbook workbook = new HSSFWorkbook();
////            FileOutputStream fos = new FileOutputStream(file);
////            workbook.write(fos);
////            if (fos != null) {
////                fos.close();
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (file.exists()) {
//            View view = new View();
//            try {
//                view.readXLSX(file.getAbsolutePath());
//                int maxRow = view.getRow();
//                if (maxRow > 0) {
//                    for (int row = 1; row <= maxRow; row++) {
//                        PasswordInfo pswInfo = new PasswordInfo();
//                        pswInfo.setId(view.getText(row, 1));
//                        pswInfo.setUsername(view.getText(row, 2));
//                        pswInfo.setPassword(view.getText(row, 3));
//                        pswInfo.setSite(view.getText(row, 4));
//                        pswInfo.setTitle(view.getText(row, 5));
//                        pswInfo.setNote(view.getText(row, 6));
//                        pswInfo.setLevel(Integer.parseInt(view.getText(row, 7)));
//                        pswInfoList.add(pswInfo);
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (CellException e) {
//                e.printStackTrace();
//            }
//        }
        return pswInfoList;
    }

    public static boolean writePswInfoListToDoc(List<PasswordInfo> pswList) {
        if (null == pswList) {
            return false;
        }
        File file = getPasswordFile();

        return writePswInfoListToXlsx(file, pswList);
    }

    public static boolean writePswInfoListToXlsx(File file, List<PasswordInfo> pswList){

//        try {
//            NPOIFSFileSystem fs = new NPOIFSFileSystem(file, true);
//            HSSFWorkbook hwb = new HSSFWorkbook(fs.getRoot(), true);
//            Biff8EncryptionKey.setCurrentUserPassword("123456");
//            HSSFSheet hssfSheet = hwb.getSheetAt(0);
//            int rowNum = pswList.size();
//            HSSFRow hssfRow;
//            for (int row = 0; row < rowNum; row++) {
//                hssfRow = hssfSheet.createRow(row + 10);
//                hssfRow.createCell(0).setCellValue(pswList.get(row).getId());
//                hssfRow.createCell(1).setCellValue(pswList.get(row).getUsername());
//                hssfRow.createCell(2).setCellValue(pswList.get(row).getPassword());
//                hssfRow.createCell(3).setCellValue(pswList.get(row).getSite());
//                hssfRow.createCell(4).setCellValue(pswList.get(row).getTitle());
//                hssfRow.createCell(5).setCellValue(pswList.get(row).getNote());
//                hssfRow.createCell(6).setCellValue(String.valueOf(pswList.get(row).getLevel()));
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            hwb.write(fos);
//            if (fos != null) {
//                fos.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return true;
    }

//    public static void encrypt(InputStream input, OutputStream output, String password)
//            throws IOException {
//
//        try {
//
//            POIFSFileSystem fs = new POIFSFileSystem();
//            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
//            // EncryptionInfo info = new EncryptionInfo(fs, EncryptionMode.agile, CipherAlgorithm.aes192, HashAlgorithm.sha384, -1, -1, null);
//
//            Encryptor enc = info.getEncryptor();
//            enc.confirmPassword(password);
//
//            OPCPackage opc = OPCPackage.open(input);
//            OutputStream os = enc.getDataStream(fs);
//            opc.save(os);
//            opc.close();
//
//            fs.writeFilesystem(output);
//            output.close();
//
//        } catch (GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidFormatException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    public static XSSFWorkbook decrypt(InputStream input, String password)
//            throws IOException {
//
//        POIFSFileSystem filesystem = new POIFSFileSystem(input);
//
//        EncryptionInfo info = new EncryptionInfo(filesystem);
//        Decryptor d = Decryptor.getInstance(info);
//
//        try {
//            if (!d.verifyPassword(password)) {
//                throw new RuntimeException(
//                        "Unable to process: document is encrypted");
//            }
//
//            InputStream dataStream = d.getDataStream(filesystem);
//
//            // parse dataStream
//            return new XSSFWorkbook(dataStream);
//
//        } catch (GeneralSecurityException ex) {
//            throw new RuntimeException("Unable to process encrypted document",
//                    ex);
//        }
//
//    }
}
