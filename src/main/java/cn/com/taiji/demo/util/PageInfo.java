package cn.com.taiji.demo.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * 对Page<E>结果进行包装
 * <p/>
 * 新增分页的多项属性 重新封装PageInfo,满足jqGrid的分页属性要求 2017-4-25
 * 
 * @author phy
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PageInfo<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	// 当前页
	private int page;
	// 每页的数量
	private int pageSize;
	// 当前页的数量
	private int size;

	// 由于startRow和endRow不常用，这里说个具体的用法
	// 可以在页面中"显示startRow到endRow 共size条数据"

	// 当前页面第一个元素在数据库中的行号
	private int startRow;
	// 当前页面最后一个元素在数据库中的行号
	private int endRow;
	// 总记录数
	private long records;
	// 总页数
	private int total;
	// 结果集
	private List<T> rows;

	// 前一页
	private int prePage;
	// 下一页
	private int nextPage;

	// 是否为第一页
	private boolean isFirstPage = false;
	// 是否为最后一页
	private boolean isLastPage = false;
	// 是否有前一页
	private boolean hasPreviousPage = false;
	// 是否有下一页
	private boolean hasNextPage = false;
	// 导航页码数
	private int navigatePages;
	// 所有导航页号
	private int[] navigatepageNums;
	// 导航条上的第一页
	private int navigateFirstPage;
	// 导航条上的最后一页
	private int navigateLastPage;

	public PageInfo() {
	}

	/**
	 * 包装Page对象
	 *
	 * @param list
	 */
	public PageInfo(List<T> list) {
		this(list, 8);
	}

	/**
	 * 包装Page对象
	 *
	 * @param list
	 *            page结果
	 * @param navigatePages
	 *            页码数量
	 */
	public PageInfo(List<T> list, int navigatePages) {
		String packName = list.getClass().getPackage().getName();
		if ("com.github.pagehelper".equals(packName)) {
			this.rows = list;
			Field[] fields = list.getClass().getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					if ("pageNum".equals(field.getName())) {
						this.page = (Integer) field.get(list);
					} else if ("pageSize".equals(field.getName())) {
						this.pageSize = (Integer) field.get(list);
					}
					if ("pages".equals(field.getName())) {
						this.total = (Integer) field.get(list);
					}
					if ("size".equals(field.getName())) {
						this.size = (Integer) field.get(list);
					}
					if ("total".equals(field.getName())) {
						this.records = (Long) field.get(list);
					}
					if ("startRow".equals(field.getName())) {
						this.startRow = (Integer) field.get(list);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Page page = (Page) list;
			// list.getClass().getMethod(name, parameterTypes)
			// this.page = page.getPageNum();
			// this.pageSize = page.getPageSize();
			//
			// this.total = page.getPages();
			// this.rows = page;
			// this.size = page.size();
			// this.records = page.getTotal();
			// 由于结果是>startRow的，所以实际的需要+1
			if (this.size == 0) {
				this.startRow = 0;
				this.endRow = 0;
			} else {
				// this.startRow = page.getStartRow() + 1;
				// 计算实际的endRow（最后一页的时候特殊）
				this.endRow = this.startRow - 1 + this.size;
			}
		} else if (list instanceof Collection) {
			this.page = 1;
			this.pageSize = list.size();

			this.total = 1;
			this.rows = list;
			this.size = list.size();
			this.records = list.size();
			this.startRow = 0;
			this.endRow = list.size() > 0 ? list.size() - 1 : 0;
		}
		if (list instanceof Collection) {
			this.navigatePages = navigatePages;
			// 计算导航页
			calcNavigatepageNums();
			// 计算前后页，第一页，最后一页
			calcPage();
			// 判断页面边界
			judgePageBoudary();
		}
	}

	/**
	 * 计算导航页
	 */
	private void calcNavigatepageNums() {
		// 当总页数小于或等于导航页码数时
		if (total <= navigatePages) {
			navigatepageNums = new int[total];
			for (int i = 0; i < total; i++) {
				navigatepageNums[i] = i + 1;
			}
		} else { // 当总页数大于导航页码数时
			navigatepageNums = new int[navigatePages];
			int startNum = page - navigatePages / 2;
			int endNum = page + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				// (最前navigatePages页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			} else if (endNum > total) {
				endNum = total;
				// 最后navigatePages页
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatepageNums[i] = endNum--;
				}
			} else {
				// 所有中间页
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			}
		}
	}

	/**
	 * 计算前后页，第一页，最后一页
	 */
	private void calcPage() {
		if (navigatepageNums != null && navigatepageNums.length > 0) {
			navigateFirstPage = navigatepageNums[0];
			navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
			if (page > 1) {
				prePage = page - 1;
			}
			if (page < total) {
				nextPage = page + 1;
			}
		}
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = page == 1;
		isLastPage = page == total;
		hasPreviousPage = page > 1;
		hasNextPage = page < total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int pageNum) {
		this.page = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long total) {
		this.records = total;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int pages) {
		this.total = pages;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> list) {
		this.rows = list;
	}

	@Deprecated
	// firstPage就是1, 此函数获取的是导航条上的第一页, 容易产生歧义
	public int getFirstPage() {
		return navigateFirstPage;
	}

	@Deprecated
	public void setFirstPage(int firstPage) {
		this.navigateFirstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	@Deprecated
	// 请用getPages()来获取最后一页, 此函数获取的是导航条上的最后一页, 容易产生歧义.
	public int getLastPage() {
		return navigateLastPage;
	}

	@Deprecated
	public void setLastPage(int lastPage) {
		this.navigateLastPage = lastPage;
	}

	public boolean isIsFirstPage() {
		return isFirstPage;
	}

	public void setIsFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	public int getNavigateFirstPage() {
		return navigateFirstPage;
	}

	public int getNavigateLastPage() {
		return navigateLastPage;
	}

	public void setNavigateFirstPage(int navigateFirstPage) {
		this.navigateFirstPage = navigateFirstPage;
	}

	public void setNavigateLastPage(int navigateLastPage) {
		this.navigateLastPage = navigateLastPage;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PageInfo{");
		sb.append("page=").append(page);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", size=").append(size);
		sb.append(", startRow=").append(startRow);
		sb.append(", endRow=").append(endRow);
		sb.append(", records=").append(records);
		sb.append(", total=").append(total);
		sb.append(", rows=").append(rows);
		sb.append(", prePage=").append(prePage);
		sb.append(", nextPage=").append(nextPage);
		sb.append(", isFirstPage=").append(isFirstPage);
		sb.append(", isLastPage=").append(isLastPage);
		sb.append(", hasPreviousPage=").append(hasPreviousPage);
		sb.append(", hasNextPage=").append(hasNextPage);
		sb.append(", navigatePages=").append(navigatePages);
		sb.append(", navigateFirstPage").append(navigateFirstPage);
		sb.append(", navigateLastPage").append(navigateLastPage);
		sb.append(", navigatepageNums=");
		if (navigatepageNums == null)
			sb.append("null");
		else {
			sb.append('[');
			for (int i = 0; i < navigatepageNums.length; ++i)
				sb.append(i == 0 ? "" : ", ").append(navigatepageNums[i]);
			sb.append(']');
		}
		sb.append('}');
		return sb.toString();
	}
}
