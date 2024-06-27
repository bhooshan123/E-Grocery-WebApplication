<?php
session_start();

// Mock array of products (in a real application, this data would come from a database)
$products = [
    1 => ['name' => 'Natraj Ball Pens', 'price' => 167],
    2 => ['name' => 'Kangaroo Stapler No.-10', 'price' => 210],
    3 => ['name' => 'Post It Sticky Notes', 'price' => 358]
];

if (!isset($_SESSION['cart'])) {
    $_SESSION['cart'] = [];
}

// Handle Add to Cart action
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $productId = $_POST['productId'];
    if (!isset($_SESSION['cart'][$productId])) {
        $_SESSION['cart'][$productId] = 0;
    }
    $_SESSION['cart'][$productId]++;
    
    // Redirect to self to avoid form resubmission on refresh
    header("Location: products.php");
    exit();
}

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Simple PHP Shopping Cart</title>
</head>
<body>
    <h1>Products</h1>
    <div>
        <?php foreach ($products as $id => $product): ?>
            <div>
                <h2><?php echo htmlspecialchars($product['name']); ?></h2>
                <p>$<?php echo number_format($product['price'], 2); ?></p>
                <form action="products.php" method="post">
                    <input type="hidden" name="productId" value="<?php echo $id; ?>">
                    <button type="submit">Add to Cart</button>
                </form>
            </div>
        <?php endforeach; ?>
    </div>
    <a href="cart.php">View Cart</a>
</body>
</html>
