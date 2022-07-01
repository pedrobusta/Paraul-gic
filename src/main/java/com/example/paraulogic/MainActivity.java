package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    public static  final String idMessage = "idMessage";
    TreeSet<String> conjuntDic = new TreeSet();
    BSTMapping<String,Integer> mapping = new BSTMapping();
    UnsortedArraySet <Character> conjunto;
    int IDbotones[] = {R.id.med,R.id.ArrIzq,R.id.ArrDer,R.id.der,R.id.izq,R.id.AbajoIzq,R.id.AbajoDer};
    boolean partidaEmpezada = false;
    int correcto = 0;
    String sol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        while(correcto < 1 && !partidaEmpezada) {
            generarConjunto();
            asignarLetrasBotones();
            llegirDic();
            sol = generarPosiblesSol();
        }

    }

    public void llegirDic(){
        InputStream is = getResources().openRawResource(R.raw.catala_filtrat);
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        try {
            String ln = r.readLine();
            while(ln!=null){
                if(ln.length() > 2){
                    conjuntDic.add(ln);
                }
                ln = r.readLine();
            }
        }catch(IOException e){
            System.out.println("ERROR: "+e.toString());
        }
    }

    public void setLletra(View view) {
        Button btn = (Button) findViewById(view.getId());
        TextView text = (TextView) findViewById(R.id.textView2);
        String cadena = text.getText().toString();
        cadena+= btn.getText();
        text.setText(cadena);
    }

    public void borrarLletra(View view){
        TextView text = (TextView) findViewById(R.id.textView2);
        String cadena = text.getText().toString();
        if(cadena.length()>0) {
            cadena = cadena.substring(0, cadena.length() - 1);
            text.setText(cadena);
        }
    }

    public void generarConjunto() {

        conjunto = new UnsortedArraySet(7);
        Random ran = new Random();
        int letra = ran.nextInt(4);
        char l = ' ';
        switch (letra){
            case 0:
                l = 'a';
                break;
            case 1:
                l = 'e';
                break;
            case 2:
                l = 'i';
                break;
            case 3:
                l = 'o';
                break;
            case 4:
                l = 'u';
                break;
        }

        for(int i=0;i<7;i++){
            if(i == 0){
                conjunto.add(l);
            }else {
                while (!conjunto.add((char) (ran.nextInt(24) + 97))) {}
            }
        }
    }


    public void asignarLetrasBotones(){

        Iterator iterador = conjunto.iterador();
        int i=0;
        Random ran = new Random();
        int x = ran.nextInt(7);
        Button btn = (Button) findViewById(IDbotones[x]);
        btn.setText(iterador.next().toString());

        while(iterador.hasNext()){

            if(i!= x) {
                btn = (Button) findViewById(IDbotones[i++]);
                btn.setText(iterador.next().toString());
            }else{
                i++;
            }
        }
    }

    public void shuffle(View view){
        Random ran = new Random();
        for(int i=IDbotones.length-1;i>2;i--){
            int j = ran.nextInt(i-2)+1;
            //swap
            int aux = IDbotones[i];
            IDbotones[i] = IDbotones[j];
            IDbotones[j] = aux;
        }
        asignarLetrasBotones();
    }

    public void introdueix(View view){

        Button btnCentral = (Button) findViewById(IDbotones[0]);
        char c = btnCentral.getText().toString().charAt(0);

        TextView text = (TextView) findViewById(R.id.textView2);
        String cadena = text.getText().toString();
        text.setText("");

        boolean trobada = false;
        int count = 0;

        if(cadena.length()>2){
            while(!trobada && count < cadena.length()){
                trobada = cadena.charAt(count++) == c;
            }
        }else{
            //PARAULA NO VÁLIDA
            Context context = getApplicationContext () ;
            CharSequence mensaje = "Les paraules han de tenir un mínim de 3 lletres!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context ,mensaje ,duration) ;
            toast.show();
        }

        if(trobada){        //letra central trobada
            if(conjuntDic.contains(cadena)) {

                Integer val = mapping.get(cadena);
                if (val != null) {
                    mapping.put(cadena, ++val);
                } else {
                    mapping.put(cadena, 1);
                }
                mostrarParaules();
            } else {
                Context context = getApplicationContext () ;
                CharSequence mensaje = "Paraula no válida!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context ,mensaje ,duration) ;
                toast.show();
            }
        }else{
            Context context = getApplicationContext () ;
            CharSequence mensaje = "La paraula ha de contenir la lletra central!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context ,mensaje ,duration) ;
            toast.show();
        }
    }

   public void mostrarParaules(){
       int nPalabras = 0;
       Iterator iterador = mapping.getIterator();
       while(iterador.hasNext()){
           nPalabras++;
           iterador.next();
       }
       String cadenaAbaix = "Has posat "+nPalabras+" paraules: ";
       iterador = mapping.getIterator();
       while(iterador.hasNext()){
           cadenaAbaix += iterador.next();
       }

       TextView textAbaix = (TextView) findViewById(R.id.mappingView);
       textAbaix.setText(cadenaAbaix);
   }

   public void showActivity(View v){
        partidaEmpezada = true;
        Intent intent = new Intent(this, newActivity.class);
        intent.putExtra(idMessage,sol);
        startActivity(intent); //ponemos en marcha la nueva pantalla
   }

   public String generarPosiblesSol(){
        String solutions = "";
        Iterator iteradorDic = conjuntDic.iterator();
        Button btnLletraCentral = (Button) findViewById(IDbotones[0]);
        String caracterCentral = (String) btnLletraCentral.getText();

        while(iteradorDic.hasNext()){
            String s = (String) iteradorDic.next();
            boolean correcta = true;
            boolean lletraCentral = false;

            for(int i=0;i<s.length() && correcta;i++){
                char c = s.charAt(i);
                if(conjunto.contains(c)){
                    if(c == caracterCentral.charAt(0)){
                        lletraCentral = true;
                    }
                }else{
                    correcta = false;
                }
            }

            if(correcta && lletraCentral && comprobarUnTuti(s)){
                solutions += " <font color='red'>"+s+"</font>"+", ";
                correcto++;
            }else if(correcta && lletraCentral){
                solutions += s+",";

            }
        }
        return solutions;
   }

   public boolean comprobarUnTuti(String pal){
        boolean tuti = false;
        boolean noTuti = false;
        String caracter = "";
        Iterator iterator = conjunto.getIterator();
        Character car = (Character) iterator.next();
        caracter += car;

        if(pal.length() >= 7){
            while(iterator.hasNext()){

                if(pal.contains(caracter)){
                    tuti = true;
                }else{
                    noTuti = true;
                }
                car = (Character) iterator.next();
                caracter = "";
                caracter += car;
            }

            if(pal.contains(caracter)){
                tuti = true;
            }else{
                noTuti = true;
            }
        }
        return tuti && !noTuti;
   }
}