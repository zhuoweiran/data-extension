package cn.csg.msg.producer.bean;

/**
 * 枚举{@code MsgType}定义了多种消息的类型
 *
 * <p>不同的类型，可以生成不同类型的消息</p>
 * <p>EText e文本类型</p>
 * <p>Json 含有&lt;?begin&gt; 和 &lt;?end&gt;的json消息</p>
 * <p>NoHeadJson 不含&lt;?begin&gt; 和 &lt;?end&gt;的json消息</p>
 * <p>CommPair 符合通讯对的json消息</p>
 * <p>UnidentifiedFile 符合可疑文件的json消息</p>
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 *
 */
public enum  MsgType {
    EText,Json,NoHeadJson,CommPair,UnidentifiedFile,UnAnalysisFile
}
