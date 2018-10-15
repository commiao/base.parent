package base.arch.component.cache.redis.constant;

/**
 * @CreateDate: 2018/7/30 9:29
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class CacheConstant {
    /**
     * CreateDate:2017年4月1日下午5:40:38
     *
     * @Description: 配置常量
     * @author:hehch
     * @version V1.0
     */
    public static class PofInterfaceCacheConfigConstant {

        /**
         * 缓存有效时间默认为60分钟, 单位:分.
         */
        public final static long POF_INTERFACE_CACHE_LIVE_TIME_ONE_HOUR = 60L;

        /**
         * 缓存有效时间默认为120分钟, 单位:分.
         */
        public final static long POF_INTERFACE_CACHE_LIVE_TIME_TWO_HOURS = 120L;
        /**
         * 三小时
         */
        public final static long POF_INTERFACE_CACHE_LIVE_TIME_THREE_HOURS = 180L;

        /**
         * 缓存有效时间为半天, 单位:小时.
         */
        public final static long POF_INTERFACE_CACHE_LIVE_TIME_HALF_DAY = 12L;

        /**
         * 缓存有效时间为一天, 单位:小时.
         */
        public final static long POF_INTERFACE_CACHE_LIVE_TIME_ONE_DAY = 24L;

        /**
         * 分页信息pagekey后缀标识
         */
        public final static String POF_INTERFACE_CACHE_PAGINATOR_PAGE_SUFFIX = "--paginator.page";

        /**
         * 分页信息limitkey后缀标识
         */
        public final static String POF_INTERFACE_CACHE_PAGINATOR_LIMIT_SUFFIX = "--paginator.limit";

        /**
         * 分页信息sizekey后缀标识
         */
        public final static String POF_INTERFACE_CACHE_PAGINATOR_SIZE_SUFFIX = "--paginator.size";

    }
}
