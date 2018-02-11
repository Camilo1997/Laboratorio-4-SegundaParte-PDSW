/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.webappsintro.controller;

import edu.eci.pdsw.stubs.servicesfacadestub.CurrencyServices;
import edu.eci.pdsw.stubs.servicesfacadestub.Producto;
import edu.eci.pdsw.stubs.servicesfacadestub.ProductsServices;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hcadavid, CamiloLopez
 */
@WebServlet(
        urlPatterns = "/pruebaWeb"
)
public class ShoppingKartBackingBean extends HttpServlet{

    ProductsServices productos = ProductsServices.getInstance();
    CurrencyServices servicios = CurrencyServices.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Writer responseWriter = resp.getWriter();
        List<Producto> listProductos = getProductos();
        responseWriter.write("<html>"
                    + "<head>"
                        + "<style>table, th {border:1px solid black;}</style>"
                    + "</head>"             
                    + "<body>"
                    + "<h1>Productos disponibles</h1>"
                    + "<table style=\"width:100%\">"
                    + "<tr>"
                        + "<th>Nombre</th> <th>Precio</th> <th>Id</th>");
        for(Producto x : listProductos){
            responseWriter.write("<tr><th>" + x.getNombre() + "</th><th>" + x.getPrecioEnUSD() +
                    "</th><th>" + x.getId() + "</th></tr>");
        }
        responseWriter.write("</tr>"
                + "</table>"
                + "<form name=\"Seleccion\" action=\"/pruebaWeb\" onsubmit=\"return seleccion()\" method=\"get\">"
                    + "Id Producto:<br>"
                    + "<input id=\"id\" type=\"text\" name=\"id\" required><br>"
                    + "<input type=\"submit\" value=\"ingresar\" onclick=\"seleccion()\">"
                + "</form>"              
                + "</body>"                
                + "</html>");
        responseWriter.flush();
    }
    
    public List<Producto> getProductos(){
        return productos.getProductos();
    }
    
    public double getTasaCambioDolar(){
        return servicios.getUSDExchangeRateInCOP();
    }
    
    
}
