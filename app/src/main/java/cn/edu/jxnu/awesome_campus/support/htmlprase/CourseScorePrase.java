package cn.edu.jxnu.awesome_campus.support.htmlprase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.utils.html.HtmlUtil;

/**
 * 使用：通过传进来html后，执行getEndList()即可获取模型对象集
 * Created by KevinWu on 2016/2/3.
 */
public class CourseScorePrase {
    private final static int GROUPSIZE = 7;//每组数据大小
    private final static String TERM_CSS = "td[valign=middle]";//学期选择css
    private final static String ITEM_CSS = "font[color=#330099]";//每个item选择css
    private final static String BACKUP_ITEM_CSS = "font[size=9pt]";//备选标签选择css
    private String praseStr;
    private List<String> resultList;
    private List<CourseScoreModel> endList;

    public List<String> getResultList() {
        return resultList;
    }
    public List<CourseScoreModel> getEndList(){
        return endList;
    }

    /**
    *构造时即执行解析，填充结果
    *@author KevinWu
    *create at 2016/2/4 18:30
    */
    public CourseScorePrase(String praseStr) {
        super();
        this.praseStr = praseStr;
        resultList = new ArrayList<>();
        endList = new ArrayList<>();
        praseData();
    }

    /**
    *解析数据
    *@author KevinWu
    *create at 2016/2/4 18:29
    */
    private void praseData() {
        try {
            HtmlUtil hu = new HtmlUtil(praseStr);
            List termList = hu.parseString(TERM_CSS);
            for (int i = 0; i < termList.size(); i++) {
                List aTermList = null;
                if (i < termList.size() - 1) {
                    String nowTerm = termList.get(i).toString();//当前学期
                    String nextTerm = termList.get(i + 1).toString();//下学期
                    String left[] = praseStr.split(nowTerm);//分割下边界
                    String right[] = left[1].split(nextTerm);//分割上边界
                    String aTerm = right[0];//一个学期的数据
                    aTermList = new HtmlUtil(aTerm).parseString(ITEM_CSS);
                } else if (i == termList.size() - 1) {
                    String nowTerm = termList.get(i).toString();//当前学期
                    String left[] = praseStr.split(nowTerm);
                    String aTerm = left[1];
                    aTermList = new HtmlUtil(aTerm).parseString(ITEM_CSS);
                }
                for (int j = 0; j < aTermList.size() - GROUPSIZE; j = j + GROUPSIZE) {
                    resultList.add(termList.get(i).toString());
                    resultList.add(aTermList.get(j).toString());
                    resultList.add(aTermList.get(j + 1).toString());
                    resultList.add(aTermList.get(j + 2).toString());
                    resultList.add(aTermList.get(j + 3).toString());
                    resultList.add(aTermList.get(j + 4).toString());
                    resultList.add(aTermList.get(j + 5).toString());
//                        resultList.add(aTermList.get(j+6).toString());
                }
                fillEndList();
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println("解析失败");//JUnit调试用
            e.printStackTrace();
        }
    }

    /**
     * 填充最终模型
     *
     * @author KevinWu
     * create at 2016/2/4 18:23
     */
    private void fillEndList() {
        for (int i = 0; i < resultList.size() - GROUPSIZE; i = i + GROUPSIZE) {
            endList.add(new CourseScoreModel(resultList.get(i).toString()
                    , resultList.get(i + 1).toString(),
                    resultList.get(i + 2).toString()
                    , resultList.get(i + 3).toString()
                    , resultList.get(i + 4).toString()
                    , resultList.get(i + 5).toString()
                    , resultList.get(i + 6).toString()));
        }
    }

}
