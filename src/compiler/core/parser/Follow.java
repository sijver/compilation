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
//Looks for "follows"
public class Follow {

    private static List<Follow> allFollowList;  //All follow already found
    private static Set<Nonterminal> followComputed; //All nonterminals for which follows already found
    private Nonterminal nonterminal;    //current nonterminal
    private List<LexicalUnit> followList;   //follows of current nonterminal

    public Follow(Nonterminal nonterminal, List<LexicalUnit> followList) {
        this.nonterminal = nonterminal;
        this.followList = followList;
    }

    //For debug. all follows
    private static void computeAllFollowList() {
        allFollowList = new LinkedList<Follow>();
        followComputed = new HashSet<Nonterminal>();
        for (Nonterminal nonterminal : Nonterminal.values()) {
            computeFollow(nonterminal);
        }
    }

    //For debug. all follows
    public static List<Follow> getAllFollowList() {
        if (allFollowList == null) {
            computeAllFollowList();
        }
        return allFollowList;
    }

    //Computes follow
    private static void computeFollow(Nonterminal nonterminal) {
        Set<LexicalUnit> follow = new HashSet<LexicalUnit>();
        if (nonterminal == Nonterminal.getStartSymbol()) {
            follow.add(LexicalUnit.getStackBottomSymbol());
        }
        for (GrammarRule rule : PerlGrammar.getGrammarRules()) {
            for (int i = 0; i < rule.getRightSideRule().size(); i++) {
                if (rule.getRightSideRule().get(i) != null && rule.getRightSideRule().get(i).getClass() == Nonterminal.class && rule.getRightSideRule().get(i) == nonterminal) {
                    if (i < rule.getRightSideRule().size() - 1) {
                        List<Object> betaRulePart = rule.getRightSideRule().subList(i + 1, rule.getRightSideRule().size());
                        Set<LexicalUnit> betaFirst = First.getFirst(betaRulePart);
                        if (betaFirst.contains(null)) {
                            betaFirst.remove(null);
                            if (rule.getLeftSideNonterminal() != nonterminal) {
                                follow.addAll(getFollow(rule.getLeftSideNonterminal()).getFollowList());
                            }
                        }
                        follow.addAll(betaFirst);
                    } else {
                        if (rule.getLeftSideNonterminal() != nonterminal) {
                            follow.addAll(getFollow(rule.getLeftSideNonterminal()).getFollowList());
                        }
                    }
                }
            }
        }
        followComputed.add(nonterminal);
        allFollowList.add(new Follow(nonterminal, new LinkedList<LexicalUnit>(follow)));
    }

    public static Follow getFollow(Nonterminal nonterminal) {

//        Debug info
//        System.out.println(nonterminal);
        if (followComputed == null) {
            allFollowList = new LinkedList<Follow>();
            followComputed = new HashSet<Nonterminal>();
        }
        if (!followComputed.contains(nonterminal)) {
            computeFollow(nonterminal);
        }
        for (Follow f : allFollowList) {
            if (f.getNonterminal() == nonterminal) {
                return f;
            }
        }
        return null;
    }

    public static String getFollowHtml() {
        StringBuilder followHtml = new StringBuilder();
        followHtml.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>Follow</title>" + HtmlStyler.getHtmlStyleLeftAlign() + "</head><body><table>");
        for (Follow f : getAllFollowList()) {
            followHtml.append("<tr><th>" + f.getNonterminal().toString() + "</th><td>");
            for (LexicalUnit unit : f.getFollowList()) {
                if (unit != null) {
                    followHtml.append(unit.toString() + " ");
                } else {
                    followHtml.append("lambda ");
                }
            }
            followHtml.append("</td></tr>");
        }
        followHtml.append("</table></body></html>");
        return followHtml.toString();
    }

    //Debug for follow
    public static void main(String[] args) {
        for (Follow f : getAllFollowList()) {
            System.out.println(f.getNonterminal().toString());
            for (LexicalUnit unit : f.getFollowList()) {
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

    public List<LexicalUnit> getFollowList() {
        return followList;
    }

}
