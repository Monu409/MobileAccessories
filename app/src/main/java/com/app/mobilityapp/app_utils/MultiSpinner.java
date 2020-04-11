/*
 *  Copyright (c)
 *  @website: http://arresto.in/
 *  @author: Arresto Solutions Pvt. Ltd.
 *  @license: http://arresto.in/
 *
 *  The below module/code/specifications belong to Arresto Solutions Pvt. Ltd. solely.
 */

package com.app.mobilityapp.app_utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.ConsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MultiSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List items;
    private Boolean[] selected;
    private String defaultText = "Please Select";
    private MultiSpinnerListener listener;
    private OnSubmit onSubmit;
    boolean is_SingleSelect = false;
    MultiSpinner clild_spiner;
    List selected_objects;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        selected[which] = isChecked;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        setSelected_Text();
    }

    public void setSelected_Text() {
        StringBuilder spinnerBuffer = new StringBuilder();
        boolean someUnselected;
        if (selected_objects == null) {
            selected_objects = new ArrayList();
        }
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                Log.e("cancel ", " is in on top " + true);
                spinnerBuffer.append(items.get(i).toString());
                selected_objects.add(items.get(i));
                spinnerBuffer.append(", \n");
            }
        }
        someUnselected = !areAllFalse(selected);

        String spinnerText;
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();

            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = defaultText;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spiner_tv, R.id.spnr_text, new String[]{spinnerText});
        adapter.setDropDownViewResource(R.layout.spiner_item);
        setAdapter(adapter);
        if (listener != null)
            listener.onItemsSelected(selected);
        if (onSubmit != null)
            onSubmit.onSelection(selected_objects);
    }


    public static boolean areAllFalse(Boolean[] array) {
        for (boolean b : array) if (b) return false;
        return true;
    }

    public void setChildSpinner(MultiSpinner childSpinner) {
        this.clild_spiner = childSpinner;
    }

    public MultiSpinner getChildSpinner() {
        return clild_spiner;
    }

    public void setSingle_select(boolean is_SingleSelect) {
        this.is_SingleSelect = is_SingleSelect;
    }

    public void setSubmitListener(OnSubmit listener) {
        this.onSubmit = listener;
    }

    public void setItemSelectListener(MultiSpinnerListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean performClick() {
        final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_dialog);
        selected_objects = new ArrayList();
        TextView text = dialog.findViewById(R.id.txt_dia);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        CheckBox slect_btn = dialog.findViewById(R.id.slect_btn);
        text.setText(defaultText);

        final ListAdapter ad = new ListAdapter(getContext(), items, selected);
        ListView listView = dialog.findViewById(R.id.item_list);
        if(items == null){
            Toast.makeText(getContext(), "Select brand first", Toast.LENGTH_SHORT).show();
        }
        else {
            listView.setAdapter(ad);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            slect_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Arrays.fill(selected, true);
                    else
                        Arrays.fill(selected, false);
                    ad.notifyDataSetChanged();
                }
            });

            listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (is_SingleSelect) {
                        Arrays.fill(selected, false);
                        selected[position] = true;
                        ad.notifyDataSetChanged();
                    } else {
                        if (selected[position]) {
                            selected[position] = false;
                        } else {
                            selected[position] = true;
                        }
                    }
                    ((CheckBox) view.findViewById(R.id.check_btn)).setChecked(selected[position]);
                }
            });

            btn_ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.setOnCancelListener(this);
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return true;
    }

    public void setItems(List items, List<String> last_selected) {
        this.items = items;

        // all selected by default
        selected = new Boolean[items.size()];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = last_selected.contains(((ConsModel) items.get(i)).getId().trim());
        }

        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spiner_tv, R.id.spnr_text, new String[]{defaultText});
        adapter.setDropDownViewResource(R.layout.spiner_item);
        setAdapter(adapter);

        if (last_selected.size() > 0) {
            setSelected_Text();
        }
    }

    public void setItems(List items) {
        this.items = items;
        selected = new Boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = false;

        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spiner_tv, R.id.spnr_text, new String[]{defaultText});
        adapter.setDropDownViewResource(R.layout.spiner_item);
        setAdapter(adapter);

    }

    public interface MultiSpinnerListener {
        void onItemsSelected(Boolean[] selected);
    }

    public interface OnSubmit {
        void onSelection(List list);
    }

    public class ListAdapter extends BaseAdapter {
        Context context;
        List items;
        Boolean[] selected;

        ListAdapter(Context context, List items, Boolean[] selected) {
            this.context = context;
            this.items = items;
            this.selected = selected;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i).toString();
        }

        @Override
        public long getItemId(int position) {
            return items.size();
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View v;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spiner_item, null);
            TextView textView = v.findViewById(R.id.spnr_text);
            textView.setText(items.get(position).toString());
            ((CheckBox) v.findViewById(R.id.check_btn)).setChecked(selected[position]);
//            if (!selected[position])
//                v.setBackgroundColor(Color.parseColor("#ffffff"));
//            else
//                v.setBackgroundColor(Color.parseColor("#818281"));
            return v;
        }


    }

}
