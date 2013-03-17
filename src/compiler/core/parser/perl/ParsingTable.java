package compiler.core.parser.perl;

import compiler.core.parser.First;
import compiler.core.parser.Follow;
import compiler.core.parser.GrammarRule;
import compiler.core.parser.Nonterminal;
import compiler.core.scanner.finiteautomaton.LexicalUnit;
import compiler.core.utils.HtmlStyler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
//Class constructs parsing table for Perl
public class ParsingTable {

    private int[][] parsingTable;   //Matrix of parsing table

    //Creates parsing table with -1 cells values - means no rule in this cell
    public ParsingTable(int nonteminalsNum, int lexicalUnitsNum) {
        parsingTable = new int[nonteminalsNum][lexicalUnitsNum];
        for(int i = 0; i <  nonteminalsNum; i++){
            for(int j = 0; j < lexicalUnitsNum; j++){
                parsingTable[i][j] = -1;
            }
        }
    }

    //Sets rule in appropriate cell. Can determine ambiguousity
    public void setRuleInCell(int nonterminalNum, int lexicalUnitNum, int ruleNum){
        if(parsingTable[nonterminalNum][lexicalUnitNum] != -1){
            System.out.println("Bad situation! Ambiguous!" + parsingTable[nonterminalNum][lexicalUnitNum] + " " + ruleNum + " in " + Nonterminal.values()[nonterminalNum] + " " + LexicalUnit.values()[lexicalUnitNum]);
        }
        parsingTable[nonterminalNum][lexicalUnitNum] = ruleNum;
    }

    public int getRuleFromCell(Nonterminal nonterminal, LexicalUnit lexicalUnit){
        return getRuleFromCell(nonterminal.ordinal(), lexicalUnit.ordinal());
    }

    public int getRuleFromCell(int nonterminalNum, int lexicalUnitNum){
        return parsingTable[nonterminalNum][lexicalUnitNum];
    }

    //Fills the parsing table
    public void fillTheParsingTable(){
        for(int i = 0; i < PerlGrammar.getGrammarRules().size(); i++){  //Loop through all the rules
            GrammarRule rule = PerlGrammar.getGrammarRules().get(i);
            List<LexicalUnit> first = new LinkedList<LexicalUnit>(First.getFirst(rule.getRightSideRule()));
            for(LexicalUnit lexicalUnitFirst : first){  //Loop through all the "firsts" of each rule
                if(lexicalUnitFirst != null){   //Add all "firsts" to table
                    setRuleInCell(rule.getLeftSideNonterminal().ordinal(), lexicalUnitFirst.ordinal(), i);
                } else {    //If rule has lambda as "first" then look for "follow"
                    List<LexicalUnit> follow = Follow.getFollow(rule.getLeftSideNonterminal()).getFollowList();
                    for(LexicalUnit lexicalUnitFollow : follow){    //Add all "follows" to table
                        setRuleInCell(rule.getLeftSideNonterminal().ordinal(), lexicalUnitFollow.ordinal(), i);
                    }
                }
            }
        }
    }

    public static String getParsingTableHtml(){
        StringBuilder parsingTableHtml = new StringBuilder();
        ParsingTable parsingTable = new ParsingTable(Nonterminal.values().length, LexicalUnit.values().length);
        parsingTable.fillTheParsingTable();
        parsingTableHtml.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>Parsing Table</title>" + HtmlStyler.HTML_STYLE + "</head><body><table>");
        parsingTableHtml.append("<tr><th></th>");
        for(LexicalUnit lexicalUnit : LexicalUnit.values()){
            parsingTableHtml.append(String.format("<th>%s</th>", lexicalUnit.name()));
        }
        parsingTableHtml.append("</tr>");
        for(int i = 0; i <  Nonterminal.values().length; i++){
            parsingTableHtml.append("<tr>");
            parsingTableHtml.append(String.format("<th>%s</th>", Nonterminal.values()[i].name()));
            for(int j = 0; j < LexicalUnit.values().length; j++){
                if(parsingTable.getRuleFromCell(i, j) != -1){
                    parsingTableHtml.append(String.format("<td>%d</td>", parsingTable.getRuleFromCell(i, j)));
                } else {
                    parsingTableHtml.append("<td>-</td>");
                }

            }
            parsingTableHtml.append("</tr>");
        }
        parsingTableHtml.append("</table></body></html>");
        return parsingTableHtml.toString();
    }

    //For debug and output the parsing table in html format
    public static void main(String[] args) {
        System.out.println(getParsingTableHtml());
    }


}
