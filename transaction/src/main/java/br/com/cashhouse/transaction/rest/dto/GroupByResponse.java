package br.com.cashhouse.transaction.rest.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class GroupByResponse<T> extends PageImpl<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final long totalElements;
	private final boolean last;
	private final int totalPages;
	private final boolean first;
	private final Sort sort;
	private final int numberOfElements;
	private final int size;
	private final int number;

	public GroupByResponse(List<T> content, Page<?> source) {
		super(content);
		totalElements = source.getTotalElements();
		last = source.isLast();
		totalPages = source.getTotalPages();
		first = source.isFirst();
		sort = source.getSort();
		numberOfElements = source.getNumberOfElements();
		size = source.getSize();
		number = source.getNumber();
	}

	@Override
	public long getTotalElements() {
		return totalElements;
	}

	@Override
	public boolean isLast() {
		return last;
	}

	@Override
	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public boolean isFirst() {
		return first;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public int getNumberOfElements() {
		return numberOfElements;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getNumber() {
		return number;
	}

}
