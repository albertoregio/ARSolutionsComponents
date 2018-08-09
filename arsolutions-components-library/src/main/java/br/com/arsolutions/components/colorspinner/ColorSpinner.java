package br.com.arsolutions.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import br.com.arsolutions.components.colorspinner.R;

import java.util.Arrays;
import java.util.List;

public class ColorSpinner extends android.widget.Spinner {

    // Declaracao das variaveis
    private ColorSpinnerAdapter colorSpinnerAdapter;
    private Integer[] colorsList;

    // Construtor da classe
    public ColorSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*
        int index;

        // Atribui atributo da ui
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColorSpinner, 0, 0);
        try {
            index = a.getInteger(R.styleable.ColorSpinner_index, 1);
        } finally {
            a.recycle();
        }
        */

        // Recupera a lista de cores disponiveis
        TypedArray array = getResources().obtainTypedArray(R.array.array_color_spinner);
        colorsList = convertArrays(array);

        colorSpinnerAdapter = new ColorSpinnerAdapter(this, this.getContext(), R.layout.color_spinner_picker, Arrays.asList(colorsList));
        //this.setSelection(index);
        this.setAdapter(colorSpinnerAdapter);
    }


    // Recupera a cor de acordo com o indice
    public int getColor(int index) {
        return colorSpinnerAdapter.getColor(index);
    }


    // Recupera o indice de acordo com a cor
    public int getIndex(int color) {
        return colorSpinnerAdapter.getIndex(color);
    }


    // Metodo auxiliar para converter um tipo de array e outro
    private Integer[] convertArrays(TypedArray array) {
        Integer[] integerArray = new Integer[array.length()];
        for (int i = 0; i < array.length(); i++) {
            integerArray[i] = array.getResourceId(i, 1);
        }
        return integerArray;
    }


    // ****************************************************************************************
    // Classe responsavel por controlar as cores disponiveis para selecao no componente spinner
    public class ColorSpinnerAdapter extends ArrayAdapter<Integer> implements SpinnerAdapter {

        //Declaracao de variaveis
        private final LayoutInflater mInflater;
        private final Context mContext;
        private final int mResource;
        private final List<Integer> colorList;
        private final ColorSpinner colorSpinner;


        // Construtor da classe
        public ColorSpinnerAdapter(ColorSpinner colorSpinner, Context context, int resource, List<Integer> objects) {
            super(context, resource, 0, objects);
            this.mContext = context;
            this.mResource = resource;
            this.colorList = objects;
            this.mInflater = LayoutInflater.from(context);
            this.colorSpinner = colorSpinner;
        }


        // Recupera a cor de acordo com o indice
        public int getColor(int index) {
            return colorList.get(index);
        }


        // Recupera o indice de acordo com a cor
        public int getIndex(int color) {
            return colorList.indexOf(color);
        }


        // Metodo responsavel pela exibicao do item selecionado no spinner
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(position, convertView, parent);

        }


        // Metodo responsavel pelo controle do evento para exibicao de demais itens do spinner
        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }


        // Cria um item de cor no componente spinner
        protected View createItemView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            // Recupera a cor a ser exibida no shape
            int color = ContextCompat.getColor(mContext, colorList.get(position));

            // Atribui a cor ao objeto shape
            final View view = mInflater.inflate(mResource, parent, false);
            final ImageView colorPicker = view.findViewById(R.id.color_spinner_circle_shape);
            final GradientDrawable shapeDrawable = (GradientDrawable) colorPicker.getDrawable();
            shapeDrawable.setStroke(0, Color.BLACK);
            shapeDrawable.setColor(color);

            // Exibe borda de selecao do item
            final View rectangle = view.findViewById(R.id.color_spinner_rectangle_shape);
            if (position == colorSpinner.getSelectedItemPosition()) {
                rectangle.setVisibility(View.VISIBLE);
            } else {
                rectangle.setVisibility(View.INVISIBLE);
            }

            return view;

        }

    }

}
