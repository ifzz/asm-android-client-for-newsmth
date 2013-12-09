package com.zfdang.asm.viewmodel;

import java.util.List;

import com.zfdang.asm.aSMApplication;
import com.zfdang.asm.data.Board;
import com.zfdang.asm.data.Subject;
import com.zfdang.asm.fragment.SubjectListFragment;
import com.zfdang.asm.util.SmthSupport;

public class SubjectListViewModel extends BaseViewModel {

	private Board m_currentBoard;
	private List<Subject> m_subjectList;

	private int m_currentPageNo = 1;
	private int m_boardType = SubjectListFragment.BOARD_TYPE_SUBJECT;

	private boolean m_isFirstIn = true;

	private boolean m_isInRotation = false;

	private SmthSupport m_smthSupport;

	public static final String SUBJECTLIST_PROPERTY_NAME = "SubjectList";

	public SubjectListViewModel() {
		m_smthSupport = SmthSupport.getInstance();
	}

	public Board getCurrentBoard() {
		return m_currentBoard;
	}

	public boolean updateCurrentBoard(Board board, String boardType) {
		boolean isNewBoard = true;
		if (m_currentBoard != null) {
			isNewBoard = !m_currentBoard.getEngName().equals(board.getEngName());
		}

		if (isNewBoard) {
			setCurrentBoard(board);
			if (boardType.equals("001")) {
				setBoardType(SubjectListFragment.BOARD_TYPE_SUBJECT);
			} else {
				setBoardType(SubjectListFragment.BOARD_TYPE_NORMAL);
			}
		}

		return isNewBoard;
	}

	public void setCurrentBoard(Board currentBoard) {
		m_currentBoard = currentBoard;
	}

	public List<Subject> getSubjectList() {
		return m_subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		m_subjectList = subjectList;
	}

	public boolean isFirstIn() {
		return m_isFirstIn;
	}

	public void setIsFirstIn(boolean isFirstIn) {
		m_isFirstIn = isFirstIn;
	}

	public int getCurrentPageNumber() {
		return m_currentPageNo;
	}

	public void setCurrentPageNumber(int pageNumber) {
		if (pageNumber < 1 || pageNumber > m_currentBoard.getTotalPageNo()) {
			return;
		}

		m_currentPageNo = pageNumber;
	}

	public int getBoardType() {
		return m_boardType;
	}

	public void gotoFirstPage() {
		m_currentPageNo = 1;
	}

	public void gotoLastPage() {
		m_currentPageNo = m_currentBoard.getTotalPageNo();
	}

	public void gotoNextPage() {
		m_currentPageNo++;
		if (m_currentPageNo > m_currentBoard.getTotalPageNo()) {
			m_currentPageNo = m_currentBoard.getTotalPageNo();
		}
	}

	public void gotoPrevPage() {
		m_currentPageNo--;
		if (m_currentPageNo < 1) {
			m_currentPageNo = 1;
		}
	}

	public String getTitleText() {
		return "[" + m_currentPageNo + "/" + m_currentBoard.getTotalPageNo() + "]" + m_currentBoard.getChsName();
	}

	public void updateBoardCurrentPage() {
		m_currentBoard.setCurrentPageNo(m_currentPageNo);
	}

	public void setBoardType(int boardType) {
		m_boardType = boardType;
	}

	public void toggleBoardType() {
		if (m_boardType == SubjectListFragment.BOARD_TYPE_SUBJECT) {
			m_boardType = SubjectListFragment.BOARD_TYPE_NORMAL;
		} else {
			m_boardType = SubjectListFragment.BOARD_TYPE_SUBJECT;
		}
	}

	public void notifySubjectListChanged() {
		notifyViewModelChange(this, SUBJECTLIST_PROPERTY_NAME);
	}

	public boolean isInRotation() {
		return m_isInRotation;
	}

	public void setIsInRotation(boolean isInRotation) {
		m_isInRotation = isInRotation;
	}

	public List<Subject> getSubjectListFromSmth(boolean isReloadPageNo) {
        String boardID = m_currentBoard.getBoardID();
        if (boardID == null || boardID.length() == 0 || boardID.equals("fake")) {
            m_currentBoard.setBoardID(m_smthSupport.getBoardIDFromName(m_currentBoard.getEngName()));
            // Log.e("getSubjectListFromSmth",
            //         String.format("update boardid from %s to %s", boardID, m_currentBoard.getBoardID()));
        }
		return m_smthSupport.getSubjectListFromMobile(m_currentBoard, m_boardType, isReloadPageNo, aSMApplication
				.getCurrentApplication().getBlackList());
	}

}