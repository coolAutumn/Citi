package citi.util.cmk.FIFO;

import java.util.ArrayList;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinYin {

	public static String converterToFirstSpell(String chines) throws Exception {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < nameChar.length; i++) {
			ArrayList<Character> temp = new ArrayList<Character>();
			if (nameChar[i] > 128) {// 中文
				String[] duoyinzi = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
				for (int j = 0; j < duoyinzi.length; j++) {
					if (nameChar[i] > 128) {// 中文
						if(!temp.contains(duoyinzi[j].charAt(0))){
							temp.add(duoyinzi[j].charAt(0));
						}
					} 
				}
			}else {
				if(!temp.contains(nameChar[i])){
					temp.add(nameChar[i]);
				}
			}
			String one="";
			for(Character c:temp){
				one+=c.charValue();
			}
			arrayList.add(one);
		}
		
		arrayList = permutation(arrayList);
		for (String a : arrayList) {
			pinyinName += a + "/";
		}
		pinyinName = pinyinName.substring(0, pinyinName.length() - 1);
		return pinyinName;
	}

	public static ArrayList<String> permutation(ArrayList<String> inputList) {
		ArrayList<String> resList = new ArrayList<String>();
		permutationInt(inputList, resList, 0, new char[inputList.size()]);
		return resList;
	}

	public static void permutationInt(ArrayList<String> inputList, ArrayList<String> resList, int ind, char[] arr) {
		if (ind == inputList.size()) {
			resList.add(new String(arr));
			return;
		}
		for (char c : inputList.get(ind).toCharArray()) {
			arr[ind] = c;
			permutationInt(inputList, resList, ind + 1, arr);
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(converterToFirstSpell("你好a"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
