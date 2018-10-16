package base.arch.component.mongo.constant;

public class MongoConstant {

	public static class QueryTypeConstant{
		/**
		 * QUERY_TYPE_VAGUE_ALL:模糊匹配 1. 
		 */
		public final static int QUERY_TYPE_VAGUE_ALL = Integer.valueOf("1");
		/**
		 * QUERY_TYPE_VAGUE_LEFT:左匹配 2. 
		 */
		public final static int QUERY_TYPE_VAGUE_LEFT = Integer.valueOf("2");
		/**
		 * QUERY_TYPE_VAGUE_RIGHT:右匹配 3. 
		 */
		public final static int QUERY_TYPE_VAGUE_RIGHT = Integer.valueOf("3");
		/**
		 * QUERY_TYPE_VAGUE_NO:全匹配. 
		 */
		public final static int QUERY_TYPE_VAGUE_NO = Integer.valueOf("4");
	}
}
