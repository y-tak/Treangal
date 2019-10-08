package com.trangal;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Squar {

    private String SfileOut;
    private String SfileIn;


    public Squar(String fileOut, String fileIn) {
        this.SfileOut = fileOut;
        this.SfileIn = fileIn;

        final String dir = System.getProperty("user.dir");

        String internalPath = this.getClass().getName().replace(".", File.separator);
        String externalPath = System.getProperty("user.dir") + File.separator + "src";
        String workDir = externalPath + File.separator + internalPath.substring(0, internalPath.lastIndexOf(File.separator));
       // System.out.println("externalPath = " + externalPath);

       // File fileInn = new File(externalPath + "/" + SfileIn);
      //  File fileOutt = new File(externalPath + "/" + SfileOut);

        File fileInn = new File(dir + "/" + SfileIn);
        File fileOutt = new File(dir + "/" + SfileOut);

        System.out.println(" созданы файлы: " + fileInn);
        System.out.println(" созданы файлы: " + fileOutt);

        if (!fileOutt.exists()) {
            try {
                fileOutt.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!fileInn.exists()) {
            try {
                fileInn.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//------------------------------------------------------

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileOutt.getAbsoluteFile());
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);

            List<String> lines = Files.readAllLines(fileInn.toPath());///для строк
            ArrayList<String> points = new ArrayList<>();////для слов

            HashMap<Float, ArrayList<Float>> hashMap = new HashMap<>();
            ArrayList<Float> sq1 = new ArrayList<>();

            float sq = 0;//массивы площадей

            int i = 0;
            if (lines.size() == 0) {
                System.out.println("пустой файл не могу прочитать данные");}
                else {


                for (String line : lines) {
                    String[] pointsSplit = line.toLowerCase() // приведение к нижнему регистру
                            .replaceAll("\\p{Alpha}", " ")// знаки препинания на пробел
                            .trim() // убираем пробелы
                            .split("\\s");

                    ArrayList<Float> mas = new ArrayList<>();////для слов

                    for (String s : pointsSplit) {
                        if (s.length() > 0) {
                            points.add(s.trim());
                            ///------------
                            mas.add(Float.parseFloat(s.trim()));
                            ///-------------
                        }
                    }
                    //System.out.println("mas = " + mas);
                    //---------заполнено массив по первой строке , отпарвить и посчитать его плозадь
                    sq = calculate(mas);
                    sq1.add(sq);
                    hashMap.put(sq, mas);
                    i++;
                }


                System.out.println("Максимальная площадь = " + Collections.max(sq1));
                System.out.println("координаты = " + hashMap.get(Collections.max(sq1)));

                StringBuilder sb = new StringBuilder();
                sb.append("Максимальная площадь = " + Collections.max(sq1));
                sb.append("\n");
                sb.append("координаты = " + hashMap.get(Collections.max(sq1)));


                byte[] buffer = sb.toString().getBytes();
                outputStream.write(buffer, 0, buffer.length);
                outputStream.close();

                //    Files.copy(fileOutt, new File("c:"+File.separator+ fileOutt.getName()).toPath());
            }
        } catch (IOException e)
            {

            e.printStackTrace();
        }


       }


    public float calculate(ArrayList<Float> fl) {
        float a = 0;

        ///----------------------проверки-------------------------------
        //----проверка на ошибку введения данных
        if (fl.size() < 6 || fl.size() > 6) {
            return 0;
        }
        //--проверить чтобы все три точки были различные
        boolean truetriangle = true;
        for (int i = 0; i < 2; i++) {
            int aa = i * 2 + 2;
            int bb = i * 2 + 3;
            if (aa >= 6) aa = 6 - aa;
            if (bb >= 6) bb = 6 - bb;

            if (fl.get(i * 2).equals(fl.get(aa)) & fl.get(i * 2 + 1).equals(fl.get(bb)))
                truetriangle = false;

        }
        if (truetriangle == false) {
            return 0;
        }

//----------------------------конец проверкам---------------


        ///подсчет одинаковые формулы
        //s=0.5*(x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2))--a
        //s=0.5*|(x2-x1)*(y3-y1)-(x3-x1)*(y2-y1)|---b

        System.out.println(" коррдинаты треугольника = " + fl.toString());
        a = (float) (0.5 * Math.abs(fl.get(0) * (fl.get(3) - fl.get(5)) + fl.get(2) * (fl.get(5) - fl.get(1)) + fl.get(4) * (fl.get(1) - fl.get(3))));
        System.out.println("площадь  = " + a);
        //   float b= (float) (0.5 * Math.abs((fl.get(2)-fl.get(0)) * (fl.get(5) - fl.get(1)) -(fl.get(4)-fl.get(0)) * (fl.get(3) - fl.get(1))));
        //  System.out.println("площадь по второй формуле 1 b = " + b);
        return a;


    }


}
