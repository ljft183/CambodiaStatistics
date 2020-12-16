package com.kjm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kjm.dao.BoardDAO;
import com.kjm.vo.CambodiaVO;


@Service
public class BoardServiceImpl implements BoardService {
	@Inject
	private BoardDAO dao;

	
	
	
	
	@Override
	public void registCambodia(CambodiaVO camvo) {
		dao.registCambodia(camvo);
	}
	@Override
	public List<CambodiaVO> getCambodiaYear(CambodiaVO camvo){
		return dao.getCambodiaYear(camvo);
		
	}
	
	@Override
	public void updateCam(CambodiaVO camvo) {
		dao.updateCam(camvo);
	}
	@Override
	public void registCambodiaList(List<CambodiaVO> list) {
		dao.registCambodiaList(list);
	}
	
	
}