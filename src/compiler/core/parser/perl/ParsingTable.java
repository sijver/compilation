package compiler.core.parser.perl;

import compiler.core.parser.First;
import compiler.core.parser.Follow;
import compiler.core.parser.GrammarRule;
import compiler.core.parser.Nonterminal;
import compiler.core.scanner.finiteautomaton.LexicalUnit;

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

    //For debug and output the parsing table in html format
    public static void main(String[] args) {
        ParsingTable parsingTable = new ParsingTable(Nonterminal.values().length, LexicalUnit.values().length);
        parsingTable.fillTheParsingTable();
        System.out.println("<html><body><table>");
        System.out.println("<tr><th></th>");
        for(LexicalUnit lexicalUnit : LexicalUnit.values()){
            System.out.println(String.format("<th>%s</th>", lexicalUnit.name()));
        }
        System.out.println("</tr>");
        for(int i = 0; i <  Nonterminal.values().length; i++){
            System.out.println("<tr>");
            System.out.println(String.format("<th>%s</th>", Nonterminal.values()[i].name()));
            for(int j = 0; j < LexicalUnit.values().length; j++){
                if(parsingTable.getRuleFromCell(i, j) != -1){
                    System.out.print(String.format("<td>%d</td>", parsingTable.getRuleFromCell(i, j)));
                } else {
                    System.out.print("<td>-</td>");
                }

            }
            System.out.println("</tr>");
        }
        System.out.println("</table></body></html>");
    }


}
