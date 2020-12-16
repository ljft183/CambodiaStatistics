package com.kjm.dao;

import java.util.List;

import com.kjm.vo.CambodiaVO;


public interface BoardDAO {



	public void registCambodia(CambodiaVO camvo);
	public List<CambodiaVO> getCambodiaYear(CambodiaVO camvo);
	public void updateCam(CambodiaVO camvo);
	public void registCambodiaList(List<CambodiaVO> list);






}