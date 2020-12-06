package stringFunctions;

public class StringJoiner {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testJoinString();
	}
	
	private static void testJoinString() {
		System.out.println(joinString(new String[] {"hello", "world"}, ";" ));
		System.out.println(joinString(new String[] {"hello", "world"}, " " ));
		System.out.println(joinString(new String[] {"some", "text", ""}, ";" ));
		System.out.println(joinString(new String[] {"", "some", "text", ""}, ";" ));
		System.out.println(joinString(new String[] {"", "some", "text"}, ";" ));
		System.out.println(joinString(new String[] {"here", "is", "text", "", "", "separated"}, ";" ));
		System.out.println(joinString(new String[] {"here", "is", "text", "", "", "separated", "", ""}, ";" ));
		System.out.println(joinString(new String[] {"", "here", "is", "text", "", "", "separated", "", ""}, ";" ));
	}
	
	/**
	 * Joins strings together, with separator between them.
	 * Empty strings are left-out, without separator used. 
	 * @param stringsForJoin stringd to be attached (joined text will begin with this string)
	 * @param separator text between two strings
	 * @return joined strings, with separator between them, if more then two strings as input
	 */
	public static String joinString(String[] stringsForJoin, String separator) {
		String joined = "";
		for(String s :  stringsForJoin) {
			if(s.length()==0)continue;
			if(joined.length()==0) {
				joined = joined + s;
			}else {
				joined = joined + separator + s;
			}
		}
		return joined;
	}

	
}
