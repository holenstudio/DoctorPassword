package com.holenstudio.doctorpassword.util;

import com.holenstudio.doctorpassword.model.PasswordInfo;
import com.jxcell.CellException;
import com.jxcell.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Holen on 2016/5/25.
 */
public class DocUtil {

    public static File getPasswordFile() {
        return new File(FileUtil.docDir(), "password.xlsx");
    }

    public static List<PasswordInfo> getPswInfoListFromDoc() {
        File file = getPasswordFile();
        List<PasswordInfo> pswInfoList = new ArrayList<>();
        if (file.exists()) {
            View view = new View();
            try {
                view.readXLSX(file.getAbsolutePath());
                int maxRow = view.getRow();
                if (maxRow > 0) {
                    for (int row = 1; row <= maxRow; row++) {
                        PasswordInfo pswInfo = new PasswordInfo();
                        pswInfo.setId(view.getText(row, 1));
                        pswInfo.setUsername(view.getText(row, 2));
                        pswInfo.setPassword(view.getText(row, 3));
                        pswInfo.setSite(view.getText(row, 4));
                        pswInfo.setTitle(view.getText(row, 5));
                        pswInfo.setNote(view.getText(row, 6));
                        pswInfo.setLevel(Integer.parseInt(view.getText(row, 7)));
                        pswInfoList.add(pswInfo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CellException e) {
                e.printStackTrace();
            }
        }
        return pswInfoList;
    }

    public static boolean writePswInfoListToDoc(List<PasswordInfo> pswList) {
        if (null == pswList) {
            return false;
        }
        File file = getPasswordFile();

        View view = new View();
        try {
            view.writeXLSX(file.getAbsolutePath());
            int maxRow = pswList.size();
            if (maxRow > 0) {
                for (int row = 1; row <= maxRow; row++) {
                    view.setText(row, 1, pswList.get(row - 1).getId());
                    view.setText(row, 2, pswList.get(row - 1).getUsername());
                    view.setText(row, 3, pswList.get(row - 1).getPassword());
                    view.setText(row, 4, pswList.get(row - 1).getSite());
                    view.setText(row, 5, pswList.get(row - 1).getTitle());
                    view.setText(row, 6, pswList.get(row - 1).getNote());
                    view.setText(row, 7, String.valueOf(pswList.get(row - 1).getLevel()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (CellException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
