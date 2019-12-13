package com.example.moveintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBack;

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 books", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 book", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText namefield= (EditText) findViewById(R.id.field_name);
        String name = namefield.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.satubukucheckbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.duabuku_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox tigaCheckBox = (CheckBox) findViewById(R.id.tigabuku_checkbox);
        boolean hastiga = tigaCheckBox.isChecked();

        CheckBox empatCheckBox = (CheckBox) findViewById(R.id.empatbuku_checkbox);
        boolean hasempat = empatCheckBox.isChecked();

        CheckBox limaCheckBox = (CheckBox) findViewById(R.id.limabuku_checkbox);
        boolean haslima = limaCheckBox.isChecked();

        CheckBox enamCheckBox = (CheckBox) findViewById(R.id.enambuku_checkbox);
        boolean hasenam = enamCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate,hastiga,hasempat,haslima,hasenam);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate,hastiga,hasempat,haslima,hasenam);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject)+name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addwhippedCream, boolean addChocolate, boolean addtiga, boolean addempat, boolean addlima, boolean addenam) {

        int basePrice = 0;
        if(addwhippedCream){
            basePrice=basePrice+99000;
        }
        if (addChocolate){
            basePrice=basePrice+200000;
        }
        if (addtiga){
            basePrice=basePrice+154000;
        }
        if (addempat){
            basePrice=basePrice+149000;
        }
        if (addlima){
            basePrice=basePrice+55000;
        }
        if (addenam){
            basePrice=basePrice+178000;
        }
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name,int price, boolean addWhippedCream, boolean addChocolate, boolean addtiga, boolean addempat,  boolean addlima, boolean addenam) {
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage += "\n" + "Buku Sukses Tanpa Modal : "+ addWhippedCream;
        priceMessage += "\n" + "Buku Insider GUIDE to Jakarta' : "+ addChocolate;
        priceMessage += "\n" + "Buku Sweet And Yummy : "+ addtiga;
        priceMessage += "\n" + "Buku Cake From My Kitchen : "+ addempat;
        priceMessage += "\n" + "Buku UKM Jaman Now : "+ addlima;
        priceMessage += "\n" + "Buku Serba Sarbi Baking : "+ addenam;
        priceMessage += "\n" + getString(R.string.order_summary_quantity) +quantity;
        priceMessage += "\n" + getString(R.string.order_summary_price) + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + getString(R.string.thankyou);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }


}