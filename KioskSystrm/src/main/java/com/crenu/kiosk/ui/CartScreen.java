package com.crenu.kiosk.ui;

import com.crenu.kiosk.cart.CartItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.crenu.kiosk.KioskSystem.cart;
import static com.crenu.kiosk.KioskSystem.uiManager;
import static com.crenu.kiosk.ui.PanelNameEntity.CART_PNAELNAME;
import static com.crenu.kiosk.ui.PanelNameEntity.MENU_PANELNAME;

public class CartScreen {
    private JPanel main;
    private JPanel itemListPanel;
    public CartScreen(){
        this.main = new JPanel();
        this.main.setLayout(new BorderLayout());
        uiManager.addPanel(CART_PNAELNAME, this.main);

        initItemList();
        initPayment();
    }

    private void initItemList(){
        itemListPanel = new JPanel();
        itemListPanel.setLayout(new FlowLayout());
        itemListPanel.setPreferredSize(new Dimension(860, 800));
        itemListPanel.setBackground(Color.YELLOW);

        for(CartItem item : cart.getCartItems()){
            addItemPanel(item);
        }

        this.main.add(itemListPanel, BorderLayout.NORTH);
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    private void addItemPanel(CartItem item){
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setPreferredSize(new Dimension(860, 100));

        JButton plusButton = new JButton("+");
        JLabel nameText = new JLabel();
        nameText.setText(item.getName());
        JLabel countText = new JLabel();
        countText.setText("Count : " + item.getCount());
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.addCount();
                countText.setText("Count : " + item.getCount());
            }
        });
        JButton minusButton = new JButton("-");
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.minusCount();
                if(item.getCount() < 1){
                    cart.removeCartItem(item);
                    itemListPanel.remove(itemPanel);
                    itemListPanel.revalidate();
                    itemListPanel.repaint();
                    return;
                }
                countText.setText("Count : " + item.getCount());
            }
        });
        itemPanel.add(plusButton, BorderLayout.WEST);
        itemPanel.add(nameText, BorderLayout.NORTH);
        itemPanel.add(minusButton, BorderLayout.EAST);
        itemPanel.add(countText, BorderLayout.CENTER);
        itemListPanel.add(itemPanel);
    }

    private void initPayment(){
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayout());
        paymentPanel.setPreferredSize(new Dimension(860, 200));
        this.main.add(paymentPanel, BorderLayout.SOUTH);
        JButton cashButton = new JButton("CASH");
        paymentPanel.add(cashButton, BorderLayout.WEST);
        JButton creditButton = new JButton("CARD");
        paymentPanel.add(creditButton, BorderLayout.EAST);
    }

}
