package by.dak.common.lang;

import java.util.Comparator;
import java.util.List;

public class Utils {


	public static <O> int getIndexInSortedList(List<O> sortedList, O newItem, Comparator<O> comparator) {
		for (int i = 0; i < sortedList.size(); i++) {
			O o = sortedList.get(i);
			if (comparator.compare(o, newItem) < 0)
				return i;
		}
		return sortedList.size();
	}

	public static <O> int getIndexInSortedList(ItemList<O> sortedList, O newItem, Comparator<O> comparator) {
		for (int i = 0; i < sortedList.size(); i++) {
			O o = sortedList.get(i);
			if (comparator.compare(o, newItem) > 0)
				return i;
		}
		return sortedList.size();
	}

}
