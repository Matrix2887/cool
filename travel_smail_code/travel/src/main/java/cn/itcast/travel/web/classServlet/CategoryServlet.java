package cn.itcast.travel.web.classServlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/categoryServlet/*")
public class CategoryServlet extends BaseServlet {

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoryService service = new CategoryServiceImpl();

        List<Category> category = service.findAll();

        Collections.sort(category,(c1,c2) ->{
            return c1.getCid() - c2.getCid();
        });

//        System.out.println(category);

        ObjectMapper mapper = new ObjectMapper();

        writeValue(category,response);

    }

}
