package org.tinylcy.serialize.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.tinylcy.serialize.Serializer;

/**
 * Created by chenyangli.
 */
public class FastJsonSerializer implements Serializer {

    public <T> byte[] serialize(T obj) {
        byte[] bytes = JSON.toJSONBytes(obj, SerializerFeature.SortField);
        return bytes;
    }

    public <T> T deserialize(byte[] bytes, Class<?> clazz) {
        T ret = JSON.parseObject(bytes, clazz, Feature.SortFeidFastMatch);
        return ret;
    }
}
