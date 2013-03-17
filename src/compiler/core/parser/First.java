package compiler.core.parser;

import compiler.core.parser.perl.PerlGrammar;
import compiler.core.scanner.finiteautomaton.LexicalUnit;
import compiler.core.utils.HtmlStyler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 */
//Class for looking of "firsts" of grammar rule
public class First {

    private Nonterminal nonterminal;    //Nonterminal for which look
    private List<LexicalUnit> firstList;    //List of all firsts for nonterminal

    public First(Nonterminal nonterminal, List<LexicalUnit> firstList) {
        this.nonterminal = nonterminal;
        this.firstList = firstList;
    }

    //For debug. Looks for all firsts.
    public static List<First> getAllFirstList() {
        List<First> allFirstList = new LinkedList<First>();
        for (Nonterminal nonterminal : Nonterminal.values()) {
            allFirstList.add(getFirst(nonterminal));
        }
        return allFirstList;
    }

    //Looks for firsts for nonterminal
    public static First getFirst(Nonterminal nonterminal) {
        Set<LexicalUnit> first = new HashSet<LexicalUnit>();
        for (GrammarRule rule : PerlGrammar.getGrammarRules()) {
            if (rule.getLeftSideNonterminal() == nonterminal) { //Add all firsts of each right side rule for nonterminal
                first.addAll(getFirst(rule.getRightSideRule()));
            }
        }
        return new First(nonterminal, new LinkedList<LexicalUnit>(first));
    }

    //Get first of right side rule
    public static Set<LexicalUnit> getFirst(List<Object> rule) {
        Set<LexicalUnit> first = new HashSet<LexicalUnit>();
        if (rule.get(0) != null && rule.get(0).getClass() == LexicalUnit.class) {   //If terminal first -> add it
            first.add((LexicalUnit) rule.get(0));
        } else if (rule.get(0) == null) {
            first.add(null);    //Add lambda to first
        } else {    //If first symbol of rule - nonterminal -> recursive search of firsts
            for (Object unit : rule) {
                if (unit.getClass() == Nonterminal.class) {
                    First f = getFirst((Nonterminal) unit);
                    first.addAll(f.getFirstList());
                    if (!f.getFirstList().contains(null)) {
                        break;
                    }
                } else if (unit.getClass() == LexicalUnit.class) {
                    first.add((LexicalUnit) unit);
                    break;
                }
            }
        }
        return first;
    }

    public static String getFirstHtml() {
        StringBuilder firstHtml = new StringBuilder();
        firstHtml.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>First</title>" + HtmlStyler.getHtmlStyleLeftAlign() + "</head><body><table>");
        for (First f : getAllFirstList()) {
            firstHtml.append("<tr><th>" + f.getNonterminal().toString() + "</th><td>");
            for (LexicalUnit unit : f.getFirstList()) {
                if (unit != null) {
                    firstHtml.append(unit.toString() + " ");
                } else {
                    firstHtml.append("lambda ");
                }
            }
            firstHtml.append("</td></tr>");
        }
        firstHtml.append("</table></body></html>");
        return firstHtml.toString();
    }

    //For debug and output of all firsts
    public static void main(String[] args) {
        for (First f : getAllFirstList()) {
            System.out.println(f.getNonterminal().toString());
            for (LexicalUnit unit : f.getFirstList()) {
                if (unit != null) System.out.print(unit.toString() + " ");
                else System.out.println("NULL ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public Nonterminal getNonterminal() {
        return nonterminal;
    }

    public List<LexicalUnit> getFirstList() {
        return firstList;
    }

}
