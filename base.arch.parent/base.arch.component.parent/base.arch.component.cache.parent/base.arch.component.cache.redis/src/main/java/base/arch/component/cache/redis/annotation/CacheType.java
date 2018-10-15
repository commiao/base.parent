package base.arch.component.cache.redis.annotation;

/**
 * CreateDate:2016年9月14日下午2:37:56
 * @Description: 缓存类型
 * @author:jannal
 * @version V1.0
 */
public enum CacheType {
    REDIS() {
        @Override
        public DataType getDataType(DataType dataType) {
            switch (dataType) {
            case STRING:
                return DataType.STRING;
           /* case HASHSET: FIXME Hashset操作会报错，目前原因不明
                return DataType.HASHSET;*/
        /*    case LIST:
                return DataType.LIST;*/
          /*  case SET:
                return DataType.SET;*/
         /*   case SORTEDSET:
                return DataType.SORTEDSET;*/
            default:
                throw new RuntimeException(dataType + "是不支持的数据类型,目前仅仅支持" + DataType.getDescription());
            }
        }
    };
    public abstract DataType getDataType(DataType dataType);
    

}
