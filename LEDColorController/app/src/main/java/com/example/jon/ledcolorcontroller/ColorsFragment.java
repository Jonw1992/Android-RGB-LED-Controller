package com.example.jon.ledcolorcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jon on 5/31/16.
 */
public class ColorsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener
{
    SeekBar red;
    SeekBar green;
    SeekBar blue;
    TextView rText;
    TextView gText;
    TextView bText;
    ImageView curColor;

    private OutputStream outputStream;
    BluetoothAdapter btAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.colors,container,false);

        red = (SeekBar) v.findViewById(R.id.red);
        red.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        green = (SeekBar) v.findViewById(R.id.green);
        green.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

        blue = (SeekBar) v.findViewById(R.id.blue);
        blue.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

        red.setMax(255);
        blue.setMax(255);
        green.setMax(255);

        rText = (TextView) v.findViewById(R.id.rText);
        int i = red.getProgress();
        rText.setText(Integer.toString(i));

        i = green.getProgress();
        gText = (TextView) v.findViewById(R.id.gText);
        gText.setText(Integer.toString(i));

        i = green.getProgress();
        bText = (TextView) v.findViewById(R.id.bText);
        bText.setText(Integer.toString(i));

        red.setOnSeekBarChangeListener(this);
        blue.setOnSeekBarChangeListener(this);
        green.setOnSeekBarChangeListener(this);


        curColor = (ImageView) v.findViewById(R.id.curColor);




       btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter != null)
        {
            if (btAdapter.isEnabled())
            {
                Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

                List<BluetoothDevice> s = new ArrayList<BluetoothDevice>();
                for (BluetoothDevice bt : pairedDevices)
                    s.add(bt);


                BluetoothDevice device = s.get(0);
                ParcelUuid[] uuids = device.getUuids();
                BluetoothSocket socket = null;
                try {
                    socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    socket.connect();
                    outputStream = socket.getOutputStream();
                    write("0,0,255\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }


               // Log.d("REDBAR", s.get(0).getName());
                //updateImageViewColors();

            }
        }


        return v;
    }

    long lasttime = 0;

    public void write(String s) throws IOException {
        //.d("REDBAR", String.valueOf(System.currentTimeMillis()-lasttime));
        if (btAdapter != null) {
            if (btAdapter.isEnabled()) {
                if (System.currentTimeMillis() - lasttime > 40) {
                    outputStream.write(s.getBytes());
                    lasttime = System.currentTimeMillis();
                }
            }
        }
    }


    public void updateImageViewColors()
    {
        int r = Integer.parseInt((String) rText.getText());
        int g = Integer.parseInt((String) gText.getText());
        int b = Integer.parseInt((String) bText.getText());

        curColor.setBackgroundColor(Color.argb(255,r,g,b));

        try {
            write(Integer.toString(r)+","+Integer.toString(g)+","+Integer.toString(b)+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        String progress = Integer.toString(seekBar.getProgress());
        if(seekBar.getId() == red.getId())
        {
            rText.setText(progress);
        }
        else if(seekBar.getId() == green.getId())
        {
            gText.setText(progress);
        }
        else
        {
            bText.setText(progress);
        }

        updateImageViewColors();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
