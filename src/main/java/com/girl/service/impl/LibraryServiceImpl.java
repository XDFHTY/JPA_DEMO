package com.girl.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.girl.enums.ResultEnums;
import com.girl.exception.LibraryException;
import com.girl.form.Novel;
import com.girl.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_URL;

@Service
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private TransportClient client;

//    public void testJDBC() throws Exception {
//        Properties properties = new Properties();
//        properties.put(PROP_URL, "jdbc:elasticsearch://127.0.0.1:9300/" + TestsConstants.TEST_INDEX_ACCOUNT);
//        properties.put(PROP_CONNECTIONPROPERTIES, "client.transport.ignore_cluster_name=true");
//        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
//        Connection connection = dds.getConnection();
//        PreparedStatement ps = connection.prepareStatement("SELECT  gender,lastname,age from  " + TestsConstants.TEST_INDEX_ACCOUNT + " where lastname='Heath'");
//        ResultSet resultSet = ps.executeQuery();
//        List<String> result = new ArrayList<String>();
//        while (resultSet.next()) {
//            result.add(resultSet.getString("lastname") + "," + resultSet.getInt("age") + "," + resultSet.getString("gender"));
//        }
//
//        ps.close();
//        connection.close();
//        dds.close();
//
//        Assert.assertTrue(result.size()==1);
//        Assert.assertTrue(result.get(0).equals("Heath,39,F"));
//    }


//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.put("url", "jdbc:elasticsearch://192.168.1.101:9300/");
//        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
//        dds.setInitialSize(1);
//        Connection connection = dds.getConnection();
//        String sql2 = "select * FROM myindex";
//        PreparedStatement ps = connection.prepareStatement(sql2);
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            //sql对应输出
//            System.out.println(resultSet.getString("字段名") );
//
//        }
//        ps.close();
//        connection.close();
//        dds.close();
//    }
//    public static void main(String[] args) {
//        String sql = "select * from book";
//
//        ElasticSql2DslParser sql2DslParser = new ElasticSql2DslParser();
////解析SQL
//        ElasticSqlParseResult parseResult = sql2DslParser.parse(sql);
////生成DSL(可用于rest api调用)
//        String dsl = parseResult.toDsl();
//
////toRequest方法接收一个clinet对象参数
//        SearchRequestBuilder searchReq = parseResult.toRequest(esClient);
////执行查询
//        SearchResponse response = searchReq.execute().actionGet();
//    }
//        public static void main(String[] args) {
//            String address = "139.199.19.82:9200";
//            try {
//                Connection connection = DriverManager.getConnection(address,null);
//                Statement statement = connection.createStatement();
//                ResultSet results = statement.executeQuery("select * from book");
//                while(results.next()){
//                    System.out.println("name:"+results.getString("name")+",page_count:"+results.getInt("page_count"));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }


//    public static void test3() throws Exception {
//        String sql = "select * from elasticsearch-sql_test_index";
//        //其中采用的是阿里的druid框架，
//        //其中 ElasticLexer 和 ElasticSqlExprParser 都是对 druid 中的 MySql 的进行了扩展
//        SQLExprParser parser = new ElasticSqlExprParser(sql);
//        SQLExpr expr = parser.expr();
//        if (parser.getLexer().token() != HeaderTokenizer.Token.EOF) {
//            throw new ParserException("illegal sql expr : " + sql);
//        }
//        SQLQueryExpr queryExpr=(SQLQueryExpr) expr;
//        //通过抽象语法树，封装成自定义的Select，包含了select、from、where group、limit等
//        Select select = new SqlParser().parseSelect(queryExpr);
//
//        AggregationQueryAction action;
//        DefaultQueryAction queryAction = null;
//        if (select.isAgg) {
//            //包含计算的的排序分组的
//            //request.setSearchType(SearchType.DEFAULT);
//            action= new AggregationQueryAction(client, select);
//        } else {
//            //封装成自己的Select对象
//            queryAction = new DefaultQueryAction(client, select);
//        }
//        // 把属性封装在SearchRequestBuilder(client.prepareSearch()获取的即ES中获取的方法)对象中
//        // 然后装饰了一下SearchRequestBuilder为自定义的SqlElasticSearchRequestBuilder
//        SqlElasticSearchRequestBuilder requestBuilder = queryAction.explain();
//        //之后就是对ES的操作
//        SearchResponse response=(SearchResponse) requestBuilder.get();
//        SearchHit[] hists = response.getHits().getHits();
//        System.out.println(hists.length);
//        for(SearchHit hit:hists){
//            System.out.println(hit.getSourceAsString());
//        }
//    }

    /**
     * 根据id查询方法
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> get(String id) {
        this.checkId(id);
        GetResponse result = client.prepareGet("book", "novel", id).get();
        if( !result.isExists()){
            throw new LibraryException(ResultEnums.NOVEL_NOT_EXIST);
        }
        return result.getSource();


    }

    int i = 0;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 添加的方法
     * @param novel
     * @return
     */
    @Override
    public Map<String, Object> add(Novel novel) {
        i =++i;
        try {
            //构建数据
            XContentBuilder content = XContentFactory.jsonBuilder()

                    .startObject()
                    .field("title", novel.getTitle())
                    .field("author", novel.getAuthor())
                    .field("word_count", novel.getWordCount())
                    .field("publish_date", sdf.format(new Date()))
                    .endObject();//构建结束

            //构建索引
            IndexResponse result = this.client.prepareIndex("book","novel")
//                    .setId((i)+"") // 如果没有设置id，则ES会自动生成一个id
                    .setSource(content)//放入文档

                    .get();

            //由于返回的是id，所以构建一个id的map
            Map<String, Object> map = new HashMap<>();
            map.put("id", result.getId());
            return map;
        } catch (IOException e) {
            log.error("【添加novel】 io错误， novel={}, e={}", novel, e);
            throw new LibraryException(ResultEnums.UNKNOWN_ERROR);
        }
    }

    /**
     * 删除方法
     * @param id 文档的id
     */
    @Override
    public void delete(String id) {
        this.checkId(id);
        DeleteResponse result = this.client.prepareDelete("book", "novel", id)
                .get();
    }

    /**
     * 更新操作
     * @param id
     * @param novel
     */
    @Override
    public void update(String id, Novel novel){
        UpdateRequest update = new UpdateRequest("novel", "book", id);
        try {
            //构建json数据
            XContentBuilder builder = XContentFactory.jsonBuilder().
                    startObject()
                    .field("title", novel.getTitle())
                    .field("author", novel.getAuthor())
                    .field("word_count", novel.getWordCount())
                    .field("publish_date", novel.getPublishDate().getTime())
                    .endObject();
            //构建builder
            update.doc(builder);
            //执行更新
            this.client.update(update).get();
        } catch (Exception e) {
            log.error("【更新novel】 更新失败， id={}, novel={}, e={}", id, novel, e);
            throw new LibraryException(ResultEnums.UPDATE_ERROR);
        }
    }

    /**
     * 检查id是否为空
     * @param id
     */
    private void checkId(String id){
        if(StringUtils.isBlank(id)){
            throw new LibraryException(ResultEnums.ID_IS_BLANK);
        }
    }

    private final static String article="book";
    private final static String content="content";
    /**
     * 创建索引并添加映射
     * 1、使用方面的不同
     * 其中我们在做索引的时候，希望能将所有的句子切分的更详细，以便更好的搜索，所以ik_max_word更多的用在做索引的时候，
     * 但是在搜索的时候，对于用户所输入的query(查询)词，我们可能更希望得比较准确的结果，
     * 例如，我们搜索“无花果”的时候，更希望是作为一个词进行查询，而不是切分为"无"，“花”，“果”三个词进行结果的召回，
     * 因此ik_smart更加常用语对于输入词的分析
     *2、效率方面的不同
     * ik_max_word分词相对来说效率更加迅速，而ik_smart的效率比不上ik_max_word
     * (个人做索引的时候将两种分词器进行尝试得出的结果，有误的话，望指正)
     *
     * ES中的Mapping
     * Mapping就是对索引库中索引的字段名称及其数据类型进行定义，类似于mysql中的表结构信息，
     * 但ES中的mapping比数据库灵活很多，它可以动态识别字段。一般不需要指定mapping字段都可以，
     * 因为ES会自动根据数据格式识别它的类型，如果你需要对某些字段添加特殊属性（如：定义使用其他分词器、是否分词、是否存库等），
     * 就必须手动添加mapping
     * @throws IOException
     */
    @Override
    public void CreateIndexAndMapping() throws Exception{

        CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate("book");

        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties") //设置之定义字段
                //字段名称
                .startObject("title")
                //数据类型
                .field("type","string")
                //指定分词器(analyzer是字段文本的分词器)
                .field("analyzer","ik_max_word")
                //指定分词（search_analyzer是搜索词的分词器:ik_smart，ik_max_word）
                .field("search_analyzer","ik_smart")
                .endObject()
                .startObject("author").field("type","string")
                .field("analyzer","ik_max_word")
                .field("search_analyzer","ik_smart")
                .endObject()
                .startObject("word_count").field("type","integer").endObject()
                .startObject("publish_date").field("type","date")
                .field("format","yyyy-MM-dd HH:mm:ss")
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping(content, mapping);
        CreateIndexResponse res=cib.execute().actionGet();
        System.out.println("----------添加映射(表结构)成功----------");
    }

}
