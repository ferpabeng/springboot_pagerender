package es.fpablo.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 
 * @author ferpabeng
 *
 */
public class PageRender<T> {

	private String url;
	private Page<T> page;
	private int totalPages;
	private int numElementsByPage;
	private int actualPage;
	private List<PageItem> pages;

	/**
	 * 
	 * @param url
	 * @param page
	 */
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<>();

		numElementsByPage = page.getSize();
		totalPages = page.getTotalPages();
		actualPage = page.getNumber() + 1;

		int from;
		int to;
		
		if (totalPages <= numElementsByPage) {
			from = 1;
			to = totalPages;
		} else {
			if (actualPage <= numElementsByPage / 2) {
				from = 1;
				to = numElementsByPage;
			} else if (actualPage >= totalPages - numElementsByPage / 2) {
				from = totalPages - numElementsByPage + 1;
				to = numElementsByPage;
			} else {
				from = actualPage - numElementsByPage / 2;
				to = numElementsByPage;
			}
		}

		for (int i = 0; i < to; i++) {
			pages.add(new PageItem(from + i, actualPage == from + i));
		}

	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getActualPage() {
		return actualPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public boolean isHasNext() {
		return page.hasNext();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}

}
