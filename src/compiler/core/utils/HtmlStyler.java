package compiler.core.utils;

/**
 * Created with IntelliJ IDEA.
 */
public class HtmlStyler {

    public final static String HTML_STYLE = "  <style type=\"text/css\">\n" +
            "    table {\n" +
            "     border-collapse: collapse; /* Отображать двойные линии как одинарные */\n" +
            "    }\n" +
            "    th {\n" +
            "\t font-size: 10pt;\n" +
            "     background: #ccc; /* Цвет фона */\n" +
            "     text-align: left; /* Выравнивание по левому краю */\n" +
            "    }\n" +
            "    td, th {\n" +
            "     border: 1px solid #800; /* Параметры границы */\n" +
            "     padding: 4px; /* Поля в ячейках */\n" +
            "\t text-align:center;\n" +
            "    } \n" +
            "\ttd {\n" +
            "\t font-size: 9pt;\n" +
            "\t}\n" +
            "  </style>\n" +
            " </head>";

    public static String getHtmlStyleLeftAlign(){
        return HTML_STYLE.replaceAll("text-align:center", "text-align:left");
    }

}
