package edu.aku.ramshasaeed.mnch.get.db;

import android.os.AsyncTask;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import edu.aku.ramshasaeed.mnch.data.AppDatabase;

/**
 * Created by openm on 19-Jul-18.
 */

public class GetAllDBData extends AsyncTask<Object, Void, Collection<?>> {

    AppDatabase db;
    String DAOclsName, DAOAbsClsFnc, DAOFnc;

    public GetAllDBData(AppDatabase db, String DAOclsName, String DAOAbsClsFnc, String DAOFnc) {
        this.db = db;
        this.DAOclsName = DAOclsName;
        this.DAOAbsClsFnc = DAOAbsClsFnc;
        this.DAOFnc = DAOFnc;
    }

    @Override
    protected Collection<?> doInBackground(Object... fnNames) {

        Collection<?> curData = new ArrayList<>();

        try {

            Method[] fn = db.getClass().getDeclaredMethods();
            for (Method method : fn) {
                if (method.getName().equals(DAOAbsClsFnc)) {

                    Class<?> fnClass = Class.forName(DAOclsName);

                    for (Method method2 : fnClass.getDeclaredMethods()) {
                        if (method2.getName().equals(DAOFnc)) {

                            /*String arg = "";
                            if (fnNames[3] != null) {

                                for (byte i = 0; i < ((String[]) fnNames[3]).length; i++) {

                                    arg += ((String[]) fnNames[3])[i];

                                    if (i + 1 != ((String[]) fnNames[3]).length) {
                                        arg += ",";
                                    }
                                }

                                curData = (Collection<?>) fnClass.getMethod(method2.getName())
                                        .invoke(db.getClass().getMethod(fnNames[1].toString()).invoke(db),arg.split(","));

                                break;
                            }*/

                            Class<?> params[] = new Class[fnNames.length];
                            for (int i = 0; i < fnNames.length; i++) {
                                if (fnNames[i] instanceof Integer) {
                                    params[i] = Integer.TYPE;
                                } else if (fnNames[i] instanceof String) {
                                    params[i] = String.class;
                                }
                            }

                            curData = (Collection<?>) fnClass.getDeclaredMethod(method2.getName(), params)
                                    .invoke(db.getClass().getMethod(DAOAbsClsFnc).invoke(db), fnNames);
                            break;
                        }
                    }

                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return curData;
    }


}
