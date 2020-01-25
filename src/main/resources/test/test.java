//POUR LE PRINTSTREAM
import java.io.*;
public class test {

  static int log2f (float f) {
    int i = 0;
    if (f < 0) return log2f(-f);
    else if (f == 1) return 0;
    else if (f < 1){
      while (f < 1) {
        i = i-1;
        f = f*2;
      }
      return i;
    }

    else {
      while (f >= 2) {
        f = f/2;
        i++;
      }
      return i;
    }

  }



  static float Puiss(float x, int n) {
    if (n == 0) return 1;
    float res = 1.0f;
    int i = 0;
    while (i<n) {
      res = res*x;
      i = i+1;
    }
    return res;
  }

//sin(30) = 0.8660255670547485
            // 0.8660255670547485
 //mathsin(30)= 0.8660254037844386


  static float tan(float f) {
    int k = 0; //definit le plus grand angle courant de rotation possible
    float X = 1.0f; //Abscisse du point Mj courant
    float Y = 0.0f; //Ordonnée du point Mj courant
    float epsilone = Puiss(1/10f,7); //Precision voulue sur l'angle teta
    //Precision absolue voulue = 2^-45 d'après poly
    // println("sin(f) not yet implemented");


      float temp;
      //initialiser les 6 premiers teta
      float teta_0 = 0.78539816339744830962f;
      float teta_1 = 0.099668652491162027378f;
      float teta_2 = 0.0099996666866652382063f;
      float teta_3 = 0.00099999966666686666652f;
      float teta_4 = 0.000099999999666666668667f ;
      float teta_5 = 0.0000099999999996666666667f;
      float teta_6 = 0.00000099999999999966666667f;

      while ((f >= teta_0) && (f >= epsilone)) {
        // System.out.println(0);
        f = f - teta_0;
        temp = X;
        X = X - Puiss(0.1f, k) * Y;
        Y = Y + Puiss(0.1f, k) * temp;
      }
      if (f < epsilone) return (Y/X);
      k = k+1;

      while ((f >= teta_1) && (f >= epsilone)) {
        // System.out.println(1);
        f = f - teta_1;
        temp = X;
        X = X - Puiss(0.1f, k) * Y;
        Y = Y + Puiss(0.1f, k) * temp;
      }
      if (f < epsilone) return (Y/X);
      k = k+1;


      while ((f >= teta_2) && (f >= epsilone)) {
        // System.out.println(2);

        f = f - teta_2;
        temp = X;
        X = X - Puiss(0.1f, k) * Y;
        Y = Y + Puiss(0.1f, k) * temp;
      }
      if (f < epsilone) return (Y/X);
      k = k+1;


      while ((f >= teta_3) && (f >= epsilone)) {
        // System.out.println(3);

        f = f - teta_3;
        temp = X;
        X = X - Puiss(0.1f, k) * Y;
        Y = Y + Puiss(0.1f, k) * temp;
      }
      if (f < epsilone) return (Y/X);
      k = k+1;


      while ((f >= teta_4) && (f >= epsilone)) {
        // System.out.println(4);

        f = f - teta_4;
        temp = X;
        X = X - Puiss(0.1f, k) * Y;
        Y = Y + Puiss(0.1f, k) * temp;
      }
      if (f < epsilone) return (Y/X);
      k = k+1;


      while ((f >= teta_5) && (f >= epsilone)) {
        // System.out.println(5);

        f = f - teta_5;
        temp = X;
        X = X - Puiss(0.1f, k) * Y;
        Y = Y + Puiss(0.1f, k) * temp;
      }
      if (f < epsilone) return (Y/X);
      k = k+1;


      while ((f >= teta_6) && (f >= epsilone)) {
        // System.out.println(6);

        f = f - teta_6;
        temp = X;
        X = X - Puiss(0.1f, k) * Y;
        Y = Y + Puiss(0.1f, k) * temp;
      }
      if (f < epsilone) return (Y/X);
      k = k+1;

      //a partir de k=7 teta_k = 10^-k
      while (f >= epsilone) {
        while ((f >= Puiss(0.1f, k)) && (f >= epsilone)) {
          // System.out.println(k);
          f = f - Puiss(0.1f, k);
          temp = X;
          X = X - Puiss(0.1f, k) * Y;
          Y = Y + Puiss(0.1f, k) * temp;
      }
      k = k+1;
      }
      return Y/X;

    }

    //CALCUL SQRT PAS TROP PRECIS
    static float SQRT(float f){
      if (f < 0)
        System.out.println("Erreur: nombre négatif ");
      float x = 0.1f;
      int i = 0;
      while (i < 100) {
        x = (0.5f)*(x + (f/x));
        i = i+1;
      }

      return x;
    }

    static float sin(float f) {
      return tan(f)/SQRT(1+tan(f)*tan(f));
        // println("sin(f) not yet implemented");
    }

    static float cos(float f) {
      return 1/SQRT(1+tan(f)*tan(f));
        // println("cos(f) not yet implemented");
    }



  //PRECIS QUE DANS UNE PLAGE DE VALEUR
  static float atan_HORNER(float f) {
    //on se limite a la puissance 9 dans
    //le Developpement en serie entière de atan
    //atan = a*f^9 + b*f^7 + c*f^5 + d*f^3 + e*f

    //initialiser les coeff sous forme de float
    float a = -0.09090909f;
    float b = 0.1111111111f;
    float c = -0.142857142f;
    float d = 0.20000000f;
    float e = -0.3333333333f;
    float g = 1.00000f;

    //On utilise la méthode de Horner
    //Pareil avec asin mais just les coeff differents
    return (((((a*f*f+b)*f*f+c)*f*f+d)*f*f+e)*f*f+g)*f;


      // println("atan(f) not yet implemented");
  }


  //PRECIS QUE DANS UNE PLAGE DE VALEUR
  // static float cos(float x) {
	// 	float a = 0.49999999999999999999999995425696f;
	// 	float b = 0.0416666666666666666660027496f;
	// 	float c = 0.001388888888888885759632f;
	// 	float d = 0.0000248015872951505636f;
	// 	float e = 0.000000275567182072f;
	// 	return 1-x *x * (a + x*x*(b-x*x*(c+x*x*(d-x*x*e))));
  // }

  static float ulp(float f) {
       if (log2f(f)<0)
        return Puiss(1/2f, -log2f(f)) * Puiss(1/2f,23);

      else
       return Puiss(2, log2f(f)) * Puiss(1/2f,23);

        // println("ulp(f) not yet implemented");
    }
    static float abs(float f) {
      if (f > 0) return f;
      else return (-f);
    }

    //METHODE DICHOTOMIQUE
    //IL FAUT QUE f(a) ET f(b) soient de signes opposés
    //VOIR COMMENT AMELIORER LA PRECISION
    //VALABLE QUE SUR 0 ET PI/2

    static float atan_aux(float f) {
      float a = 0.0f;
      float b = 1.0f;
      //epsilone pour la precision
      float epsilone = Puiss(1/10f,7);
      float m = 0.0f;
      while ((b-a) > epsilone) {
        m = (a+b)/2;
        // System.out.println(m);
        //if (f(a)*f(m)<=0)
        if (((tan(a)-f) * (tan(m)-f)) <= 0)
         b = m;
        else a = m;
      }
      return m;
    }

    static float atan(float f) {
      float pi_sur_2 = 1.5707963267948966f;
      if ((f >= -0.625452f) && (f <= 0.625452f))
       return atan_HORNER(f);
      else if ((f >= 0.625452f) && (f <= 1.0f)){
        return atan_aux(f);
      }
      else if ((f < -0.625452f) && (f >= -1)) {
        //ATAN IMPAIRE
        return -atan_aux(-f);
      }
      //Utilisation des equations fonctionnelles
      else if (f > 1.0f) return pi_sur_2 - atan_aux(1.0f/f);
      else return -pi_sur_2 + atan_aux(-1.0f/f);

    }

    static float asin(float f) {
      if ((f < -1) || (f > 1)) {
        System.out.println("L'arcsin n'est définit que sur [-1,1]");
        return 9999999;
      }

      return 2*atan(f/(1+SQRT(1-f*f)));
    }


  public static void main(String args[])throws FileNotFoundException {



    //Test de SQRT
    System.out.println("SQRT " + SQRT(2));
    System.out.println(" SQRT_MATH " + (float)Math.sqrt(2));
    System.out.println();

    //Test de cos sur [0,pi/2]
    System.out.println("cos_CORDIC() = " + cos(0.01f));
    System.out.println(" cos_MATH()= " + (float)Math.cos(0.01f));
    System.out.println();

    //Test de sin sur [0,pi/2]
    System.out.println("sin_CORDIC() = " + sin(0.01f));
    System.out.println(" sin_MATH()= " + ((float)Math.sin(0.01f)));
    System.out.println();

    //Test de asin sur [-1,1]
    System.out.println("asin_DICHOTOMIE() = " + asin(0.8f));
    System.out.println(" asin_MATH() = " + (float)Math.asin(0.8f));
    System.out.println();

    //test de atan sur R
    System.out.println("atan_DICHOTOMIE() = " + atan(-6956497781.0f));
    System.out.println(" atan_MATH() = " + (float)Math.atan(-6956497781.0f));
    System.out.println();


    // System.out.println(Math.PI/2);

    // System.out.println(3.141592653589793f);




     //POUR GENERER UN FICHIER .txt QUI CONTIENT LES ABSCISSES
     //AVEC LEURS IMAGES
    // File output = new File("x_atan_y_atan_HORNER.txt");
    // PrintStream x_atan_y_atan_HORNER = new PrintStream(output);
    //
    // float x = -1.0f;
    // //POUR LE MOMENT SIN ET COS SUR 0 ET PI/2
    // while (x <= 1.0f) {
    //   x_atan_y_atan_HORNER.print(x);
    //   x_atan_y_atan_HORNER.print(" ");
    //   x_atan_y_atan_HORNER.println(abs((float)Math.atan(x) - atan_HORNER(x)));
    //   x = x + 0.0007964f;
    // }


    // System.out.println(Math.ulp(0f/0));

    //GENERER UN TABLEAU CONTENANT LES ABSCISSES ET LEURS IMAGES
    // File output1 = new File("y_horner_asin.ods");
    // PrintStream y_horner_asin = new PrintStream(output1);
    //
    // float y = -1.0f;
    // while (y <= 1.0) {
    //   y_horner_asin.println(asin(y));
    //   y = y + 0.0007964f;
    // }
    //
    // File output2 = new File("y_math_asin.ods");
    // PrintStream y_math_asin = new PrintStream(output2);
    // float z = -1.0f;
    // while (z <= 1.0) {
    //   y_math_asin.println((float)Math.asin(z));
    //   z = z + 0.0007964f;
    // }



//SQRT OK
//COS ET SIN OK SUR [0,PI/2]
//ASIN ET ATAN OK!

}
}
