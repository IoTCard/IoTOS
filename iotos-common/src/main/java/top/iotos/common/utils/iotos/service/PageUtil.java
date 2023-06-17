package top.iotos.common.utils.iotos.service;


public class PageUtil {
		private int pageSize;//页面显示多少行
		private int rowCount;//总行数
		private int pageCount;//总页数
		private int nextPage;//下一页
		private int prePage;//上一页
		private int firstPage=1;//第一页
		private int lastPage;//最后一页
		private int currenPage;//当前是第几页
		private int starRow;//开始行数
		private int endRow;//结束行数
		
		
		/**
		 *  分页的构造函数
		 * @param rCount(总数)
		 * @param curren(当前页)
		 * @param pSize(一页显示条数)
		 */
		public PageUtil(Integer rCount, String curren, String pSize) {
			rCount = rCount != null ? rCount : 10;
			curren = curren != null ? curren : "0";
			pSize = pSize != null ? pSize: "10";

			this.pageSize= Integer.parseInt(pSize);
			this.rowCount = rCount;
			this.currenPage = Integer.parseInt(curren);
			
			//(给总页数赋值) 判断总行数是不是每页显示的倍数
			if(rowCount % pageSize==0){
				//给总页数赋值 总行数/需要分页的行数
				pageCount=rowCount/pageSize;
			}else{
				//否则 总页数赋值 总行数/需要分页的行数+1
				pageCount=rowCount/pageSize+1;
			}
			//(给当期那页数赋值)如果当前页小于或等于零 将当前页赋值为1
			if(currenPage<=0){
				//给当前页数一个值1
				currenPage=1;
			}
			
			// (进行上一页操作)如果当前页大于1并且小于或等于总页数 则上一页可以减1
			if(currenPage>1 && currenPage<=pageCount){
				//点击上一页时 新的当前页面=当前页面-1
				prePage=currenPage-1;
			}else{
				//否则点击第一页时还是第一页
				prePage=firstPage;
			}
			// 赋值总页数
			lastPage=pageCount;
			//(给下一页赋值) 如果当前页大于0并且小于总页数 则下一页可以加1
			if(currenPage>0 && currenPage<pageCount){
				//下一页等于当前页-1
				nextPage=currenPage+1;
			}else{
				nextPage=lastPage;
			}
			// 起始行 (当前页-1)*页面显示行数大小   
			this.starRow=(currenPage-1)*pageSize;
			// 结束行 (当前页)*页面显示行数大小
			this.endRow=currenPage*pageSize;
			
			
		}
		public int getPageSize() {
			return pageSize;
		}
		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		public int getRowCount() {
			return rowCount;
		}
		public void setRowCount(int rowCount) {
			this.rowCount = rowCount;
		}
		public int getPageCount() {
			return pageCount;
		}
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}
		public int getNextPage() {
			return nextPage;
		}
		public void setNextPage(int nextPage) {
			this.nextPage = nextPage;
		}
		public int getPrePage() {
			return prePage;
		}
		public void setPrePage(int prePage) {
			this.prePage = prePage;
		}
		public int getFirstPage() {
			return firstPage;
		}
		public void setFirstPage(int firstPage) {
			this.firstPage = firstPage;
		}
		public int getLastPage() {
			return lastPage;
		}
		public void setLastPage(int lastPage) {
			this.lastPage = lastPage;
		}
		public int getCurrenPage() {
			return currenPage;
		}
		public void setCurrenPage(int currenPage) {
			this.currenPage = currenPage;
		}
		public int getStarRow() {
			return starRow;
		}
		public void setStarRow(int starRow) {
			this.starRow = starRow;
		}
		public int getEndRow() {
			return endRow;
		}
		public void setEndRow(int endRow) {
			this.endRow = endRow;
		}
}
