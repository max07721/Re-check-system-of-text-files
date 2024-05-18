package com.project.demo.util;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.math.BigInteger;
import java.util.*;




public class SimilarityTwoUtils {


    /**
     * 1.场景：
     *         在很多公司的文件管理系统中，都有类似于对比多篇文章的相似度，例如在写公众号推文时，如果标记了原创，
     *         就会对比当前文章和库里已存在文章的相似程度，如果相似度过于高，则标记为原创的文章无法实现推送。
     *
     * 2.算法：
     *         此例子借助的是海明距离的实现方式，，此处不做过多的阐述。
     */

    /**
     * 标题名称
     */
    private String topicName;
    /**
     * 分词向量
     */
    private BigInteger bigSimHash;
    /**
     * 初始桶大小
     * 备注：对每条文本根据SimHash 算出签名后，再计算两个签名的海明距离（两个二进制异或后1的个数）即可。根
     * 据经验值，对64位的SimHash，海明距离在3以内的可以认为相似度比较高。
     * 假设对64位的SimHash，查找海明距离在3以内的所有签名。
     * 可以把64位的二进制签名均分成4块，每块16位。根据鸽巢原理（也称抽屉原理，见组合数学），如果两个签名的海明距离在3以内，它们必有一块完全相同。
     * 把上面分成的4块中的每一个块分别作为前16位来进行查找。建立倒排索引。
     */
    private Integer hashCount = 64;
    /**
     * 分词最小长度限制
     */
    private static Integer WORD_MIN_LENGTH = 3;

    private static final BigInteger ILLEGAL_X = new BigInteger("-1");

    public SimilarityTwoUtils(String topicName, Integer myHashCount, Integer wordMinLength) {

        this.WORD_MIN_LENGTH = wordMinLength;

        this.topicName = topicName;
        this.bigSimHash = this.simHash();

        //如果myHashCount为null，则默认64
        if (null != myHashCount) {
            this.hashCount = myHashCount;
        }
    }

    /**
     * 分词计算向量
     *
     * @return BigInteger
     */
    private BigInteger simHash() {
        this.topicName = this.clearSpecialCharacters(this.topicName); // 清洗文本
        int[] hashArray = new int[this.hashCount]; // 初始化哈希数组

        // 对内容进行分词处理
        List<Term> terms = StandardTokenizer.segment(this.topicName);

        // 配置词性权重，为关键词性增加权重
        Map<String, Integer> weightMap = new HashMap<>(16, 0.75F);
        weightMap.put("n", 2); // 名词权重增加，因为名词常包含关键概念
        weightMap.put("v", 2); // 动词权重增加，动词可能是关键动作或过程的一部分
        weightMap.put("adj", 2); // 形容词，可能用于描述重要特征

        // 设置停用词
        Map<String, String> stopMap = new HashMap<>(16, 0.75F);
        stopMap.put("u", ""); // 介词等通常不带有实质内容
        stopMap.put("c", ""); // 连词也往往不重要

        // 设置超频词上限，适当调高阈值以反映学术文本特性
        Integer overCount = 10; // 学术作业中重要词汇可能较为集中

        // 分词统计量，用于控制超频词
        Map<String, Integer> wordMap = new HashMap<>(16, 0.75F);

        for (Term term : terms) {
            String word = term.word;
            String nature = term.nature.toString();

            // 过滤超频词
            Integer count = wordMap.getOrDefault(word, 0);
            if (count > overCount) {
                continue;
            }
            wordMap.put(word, count + 1);

            // 过滤停用词
            if (stopMap.containsKey(nature)) {
                continue;
            }

            // 计算单个分词的Hash值
            BigInteger wordHash = this.getWordHash(word);

            for (int i = 0; i < this.hashCount; i++) {
                BigInteger bitMask = new BigInteger("1").shiftLeft(i);
                Integer weight = weightMap.getOrDefault(nature, 1); // 默认权重为1
                if (wordHash.and(bitMask).signum() != 0) {
                    hashArray[i] += weight;
                } else {
                    hashArray[i] -= weight;
                }
            }
        }

        // 生成指纹
        BigInteger fingerPrint = new BigInteger("0");
        for (int i = 0; i < this.hashCount; i++) {
            if (hashArray[i] >= 0) {
                fingerPrint = fingerPrint.add(new BigInteger("1").shiftLeft(i));
            }
        }

        return fingerPrint;
    }


    /**
     * 计算单个分词的hash值
     *
     * @return BigInteger
     */
    private BigInteger getWordHash(String word) {
        if (StringUtils.isEmpty(word)) {
            return BigInteger.ZERO; // 处理空字符串或null
        }

        // 保证所有单词至少达到最小长度
        if (word.length() < WORD_MIN_LENGTH) {
            word = StringUtils.rightPad(word, WORD_MIN_LENGTH, word.charAt(0));
        }

        // 哈希初始值和基数
        BigInteger x = BigInteger.valueOf(word.charAt(0) << 7);
        BigInteger m = new BigInteger("1000003");
        BigInteger mask = BigInteger.ONE.shiftLeft(hashCount).subtract(BigInteger.ONE);

        // 遍历每个字符，计算哈希值
        for (char ch : word.toCharArray()) {
            BigInteger temp = BigInteger.valueOf(ch);
            x = x.multiply(m).xor(temp).and(mask);
        }

        // 将单词长度也包含到哈希值中
        x = x.xor(BigInteger.valueOf(word.length()));

        // 检查非法哈希值并做适当处理
        if (x.equals(ILLEGAL_X)) {
            x = new BigInteger("-2");
        }

        return x;
    }


    /**
     * 过滤特殊字符
     *
     * @return BigInteger
     */
    private String clearSpecialCharacters(String topicName) {

        // 将内容转换为小写并去除HTML标签
        topicName = Jsoup.clean(topicName.toLowerCase(), Whitelist.none());

        // 使用正则表达式一次性替换所有特殊字符
        topicName = topicName.replaceAll("[\\s\\n\\r\\t&<>\"']", "");

        return topicName;
    }

    /**
     * 获取内容的相似度
     *
     * @return Double
     */
    public Double getSimilar(SimilarityTwoUtils simHashUtil) {

        // 获取海明距离
        Double hammingDistance = (double) this.getHammingDistance(simHashUtil);

        // 求得海明距离百分比
        Double scale = (1 - hammingDistance / this.hashCount) * 100;

        Double formatScale = Double.parseDouble(String.format("%.2f", scale));

        return formatScale;
    }

    /**
     * 获取内容的海明距离
     *
     * @return Double
     */
    private int getHammingDistance(SimilarityTwoUtils simHashUtil) {

        // 直接求异或
        BigInteger xor = this.bigSimHash.xor(simHashUtil.bigSimHash);

        // 使用 bitCount 方法计算海明距离
        return xor.bitCount();
    }


}
