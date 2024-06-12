

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin Page</title>
    <link rel="stylesheet" href="css/style.css">
    <%@include file="components/common_css_js.jsp" %>
</head>
<body>
    
    
    <%@include file="components/navbar.jsp" %>
    
    <div class="card_section">
        <div class="card_box1">
            <img src="img/team.png" alt="user">
            <div class="text">
                <h3>234</h3>
                <p>USERS</p>
            </div>
        </div>
        
        <div class="card_box1">
            <img src="img/shopping-cart.png" alt="user" class="card_icon_image">
            <div class="text">
                <h2>05</h2>
                <p>CATEGORIES</p>
            </div>
        </div>
        
        <div class="card_box1">
            <img src="img/online-shopping.png" alt="user">
            <div class="text">
                <h2>150</h2>
                <p>PRODUCTS</p>
            </div>
        </div>
    </div>
    
    <div class="card_section">
        <div class="card_box">
            <img src="img/add-cart.png" alt="user"><br><br>
            <h3>ADD NEW CATEGORY</h3>
            <p>Simply click here to seamlessly introduce new product categories to your store.</p>
            <br><button data-bs-toggle="modal" data-bs-target="#staticBackdrop">Click Here</button>
        </div>
        
        <div class="card_box">
            <img src="img/add-package.png" alt="user"><br><br>
            <h3>ADD NEW PRODUCT</h3>
            <p>Easily expand your product range with a simple click. Seamlessly add new items to your inventory.</p><br>
            <button type="button"  data-bs-toggle="modal" data-bs-target="#productModal" data-bs-whatever="@mdo">Click Here</button>
        </div>
    </div>

  
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true" style="justify-content: center">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="staticBackdropLabel">Fill Category Details</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
   <div class="modal-body">
    <form action="ProductOparetionServlet" >
        
        <div class="mb-3">
            <input type="hidden" name="operation" value="addcategory">
            <input type="text" class="form-control" name="catTitle" style="border: solid 1px gray" placeholder="Enter Category title" required>
        </div>
        <div class="mb-3">
            <textarea class="form-control" name="catDisc" style="border: solid 1px gray; resize: none; height: 350px" placeholder="Enter Category Description"></textarea>
        </div>
        <button type="submit" class="btn btn-primary" style="background-color: black; border: none">Add Category</button>
    </form>
</div>
      
    </div>
  </div>
</div>
    

<div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">Open modal for
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Add new Product</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form action="ProductOperationServlet">
    <div class="mb-3">
        <input type="hidden" name="operation" value="addproduct">
        <input type="text" class="form-control" name="pName" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Name" required>
    </div>
    <div class="mb-3">
        <textarea class="form-control" name="pDesc" style="border: solid 1px gray;font-size:15px;resize: none;" placeholder="Enter Product Description"></textarea>
    </div>
    <div class="mb-3">
        <label for="productPhoto" style="font-size:15px">Product Photo:</label>
    <input type="file" class="form-control" name="pPhoto" style="border: solid 1px gray;font-size:15px" placeholder="Choose Product Photo">
</div>
    <div class="mb-3">

        <input type="number" class="form-control" name="pPrice" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Price" required>
    </div>
    <div class="mb-3">
        <input type="number" class="form-control" name="pDiscount" style="border: solid 1px gray;font-size:15px" placeholder="Enter Product Discount">
    </div>
    <div class="mb-3">
        <select class="form-select" name="categoryId" style="border: solid 1px gray;font-size:15px" required>
            <option value="" selected disabled>Select Category</option>
           
        </select>
    </div>
            
     <div class="mb-3">
        <input type="number" class="form-control" name="pDiscount" style="border: solid 1px gray" placeholder="Enter Product Discount">
    </div>
            
            
    <button type="submit" class="btn btn-primary" style="background-color: black; border: none">Add Product</button>
</form>

      </div>
      
    </div>
  </div>
</div>

</body>
</html>
