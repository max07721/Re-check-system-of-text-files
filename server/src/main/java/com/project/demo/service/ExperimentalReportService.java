package com.project.demo.service;

import com.project.demo.entity.ExperimentalReport;
import com.project.demo.service.base.BaseService;
import com.project.demo.util.PDFUtils;
import com.project.demo.util.SimilarityTwoUtils;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;
import java.util.*;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;
import java.util.stream.Collectors;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;

/**
 * 实验报告：(ExperimentalReport)表服务接口
 */
@Service
public class ExperimentalReportService extends BaseService<ExperimentalReport> {

    private static final String BEGIN_STR = "<p>";
    private static final String AFTER_STR = "</p>";
    private static boolean IS_CHINESE = false;

    public void exportPDF(OutputStream baos, String experimental_report_id) {
        // 实例化文档对象
        Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        try {
            // 创建 PdfWriter 对象 文件的输出路径+文件的实际名称
            PdfWriter.getInstance(document, baos);
            // writer.setPageEvent(new PDFMaker());
            document.open();// 打开文档
            // 标题
            String[] title = {"课程名称", "学生姓名", "教师姓名", "实验时间","实验地点","实验成绩","一、实验题目",  "二、实验内容",  "三、实验解答", "四、批阅评语"};
            // 列宽
            int tableWidth[] = {10, 10, 10, 10, 10, 10};
            Map<String, String> query = new HashMap<>(16);
            query.put("experimental_report_id", experimental_report_id);
            ExperimentalReport experimentalReport = this.findOne(query);
            // 创建一个段落
            document.add(PDFUtils.getPDFParagraph("实验报告",0, 20, Element.ALIGN_CENTER, 1));
            if (!Objects.isNull(experimentalReport)) {
                // 获取PDFTable
                PdfPTable table = PDFUtils.getPDFTableByObjec(tableWidth, title, experimentalReport, 0);
                // 添加进文档
                document.add(table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭文档
                if (document != null) {
                    document.close();
                }
                if (document != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String similarity(String articleAId, String otherArticleId) {
        ExperimentalReport articleA = this.findOne(new HashMap<String, String>() {{
            put("experimental_report_id", articleAId);
        }});
        ExperimentalReport otherArticle = this.findOne(new HashMap<String, String>() {{
            put("experimental_report_id", otherArticleId);
        }});


        if (articleA != null && otherArticle != null) {
            String[] sentencesA = getSubString(articleA.getExperimental_solution());
            String[] sentencesB = getSubString(otherArticle.getExperimental_solution());

            for (int i = 0; i < sentencesA.length; i++) {
                sentencesA[i] = processString(sentencesA[i]);
            }

            for (int i = 0; i < sentencesB.length; i++) {
                sentencesB[i] = processString(sentencesB[i]);
            }
            System.out.println(otherArticleId + sentencesA.length + "," + sentencesB.length);
            Integer wordMinLength = IS_CHINESE ? 1: 3;
            Integer myHashCount = IS_CHINESE ? 24: 64;
            StringBuilder comparisonResults = new StringBuilder();
            for (int i = 0; i < sentencesA.length; i++) {
                String sentenceA = sentencesA[i];
                double highestSimilarity = 0;
                int mostSimilarIndex = -1;
                for (int j = 0; j < sentencesB.length; j++) {
                    String sentenceB = sentencesB[j];
                    SimilarityTwoUtils simHashA = new SimilarityTwoUtils(sentenceA, myHashCount, wordMinLength);
                    SimilarityTwoUtils simHashB = new SimilarityTwoUtils(sentenceB, myHashCount, wordMinLength);
                    double similarity = simHashA.getSimilar(simHashB);
                    System.out.println(similarity);
                    if (similarity >= highestSimilarity) {
                        highestSimilarity = similarity;
                        mostSimilarIndex = j;
                    }
                }

                comparisonResults.append(i).append("_").append(mostSimilarIndex).append("_").append(String.format("%.2f", highestSimilarity)).append(", ");

            }
            // Remove the trailing comma and space
            if (comparisonResults.length() > 0) {
                comparisonResults.setLength(comparisonResults.length() - 2);
            }
            System.out.println(otherArticleId + "?" + comparisonResults);
            return (otherArticleId + "?" + comparisonResults);
        }
        return "";
    }

    /**
     * 截取标签 <p> </p>
     */
    private String[] getSubString(String str){
        System.out.println(str);
        String[] paragraphs = str.split("</p><p>");
        paragraphs[0] = paragraphs[0].replaceFirst("<p>", "");
        paragraphs[paragraphs.length - 1] = paragraphs[paragraphs.length - 1].replaceFirst("</p>", "");
        // Print each paragraph without the HTML tags
        for (String paragraph : paragraphs) {
            System.out.println(paragraph.trim());
            System.out.println();
        }
        return paragraphs;
    }


    public String processString(String text) {
        String processed = "";
        if (isChinese(text)) {
            processed = TextPreprocessor(text);
        } else {
            processed = EnglishTextPreprocessor(text);
        }
        return processed;
    }

    private static boolean isChinese(String text) {
        int chineseCharacterCount = 0;
        for (char ch : text.toCharArray()) {
            if (Character.toString(ch).matches("[\\p{IsHan}]")) {
                chineseCharacterCount++;
            }
        }
        IS_CHINESE = (double) chineseCharacterCount / text.length() > 0.2;
        return IS_CHINESE;
    }
    public String EnglishTextPreprocessor(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        Set<String> stopwords = new HashSet<>(Arrays.asList("a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can", "cannot", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "her", "here", "hers", "him", "his", "how", "i", "if", "in", "into", "is", "it", "its", "itself", "just", "me", "more", "most", "my", "myself", "no", "nor", "not", "now", "of", "off", "on", "once", "only", "or", "other", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "should", "so", "some", "such", "than", "that", "the", "their", "theirs", "them", "themselves", "then", "there", "these", "they", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "were", "what", "when", "where", "which", "while", "who", "whom", "why", "with", "would", "you", "your", "yours", "yourself", "yourselves"));
        List<String> filteredTokens = new ArrayList<>();
        for (CoreSentence sentence : document.sentences()) {
            for (CoreLabel token : sentence.tokens()) {
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                if (!stopwords.contains(lemma)) {
                    filteredTokens.add(lemma);
                }
            }
        }
        return  String.join(" ", filteredTokens);
    }

    public String TextPreprocessor(String text) {
        String cleanText = text.replaceAll("[\\pP\\p{Punct}]", "");
        String lowercaseText = cleanText.toLowerCase();
        Segment segment = HanLP.newSegment();
        List<Term> termList = segment.seg(lowercaseText);
        CoreStopWordDictionary.apply(termList);
        String processedText = termList.stream()
                .map(term -> term.word)
                .collect(Collectors.joining(""));
        return processedText;
    }
}
