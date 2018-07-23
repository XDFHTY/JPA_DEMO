package com.girl.ppl;


/**
 * HanLP是由一系列模型与算法组成的Java工具包，目标是普及自然语言处理在生产环境中的应用。不仅仅是分词，
 * 而是提供词法分析、句法分析、语义理解等完备的功能。HanLP具备功能完善、性能高效、架构清晰、语料时新、可自定义的特点。
 *
 * HanLP完全开源，包括词典。不依赖其他jar，底层采用了一系列高速的数据结构，如双数组Trie树、DAWG、AhoCorasickDoubleArrayTrie等，
 * 这些基础件都是开源的。官方模型训练自2014人民日报语料库，您也可以使用内置的工具训练自己的模型。
 *
 * 通过工具类HanLP您可以一句话调用所有功能，文档详细，开箱即用。底层算法经过精心优化，极速分词模式下可达2,000万字/秒，
 * 内存仅需120MB。在IO方面，词典加载速度极快，只需500 ms即可快速启动。HanLP经过多次重构，欢迎二次开发。
 */
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

public class HanLPDemo {

    public void HanLPPPL(String[] strings){
        for (String text : strings){
            List<Term> termList = HanLP.segment(text);
            for (Term term:termList){
                System.out.print(">"+term.word);
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        String[] testCase = new String[]{
                "商品和服务",
                "结婚的和尚未结婚的确实在干扰分词啊",
                "买水果然后来世博园最后去世博会",
                "中国的首都是北京",
                "欢迎新老师生前来就餐",
                "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作",
                "随着页游兴起到现在的页游繁盛，依赖于存档进行逻辑判断的设计减少了，但这块也不能完全忽略掉。",
        };

            new HanLPDemo().HanLPPPL(testCase);
    }
}