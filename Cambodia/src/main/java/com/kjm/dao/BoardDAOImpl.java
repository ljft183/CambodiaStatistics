package com.kjm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kjm.vo.CambodiaVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Inject 
	private SqlSession session; 
	private static String namespace = "com.kjm.mapper.BoardMapper";

	
	
	
	@Override
	public void registCambodia(CambodiaVO camvo) {
		session.insert(namespace+".registCambodia",camvo);
	}
	@Override
	public List<CambodiaVO> getCambodiaYear(CambodiaVO camvo){
		return session.selectList(namespace+".getCambodiaYear",camvo);
	}
	@Override
	public void updateCam(CambodiaVO camvo) {
		session.update(namespace+".updateCam",camvo);
	}
	@Override
	public void registCambodiaList(List<CambodiaVO> list) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("camList",list);
		session.insert(namespace+".registCambodiaList",map);
	}

}