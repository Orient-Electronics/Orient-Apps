package com.orient.profresh;

import java.util.Iterator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MyColorDialog extends AlertDialog {

        private static int NONE = -1;

        private int tint = NONE;
     

      /**

       * @param context

       * @param theme

       */

      protected MyColorDialog(Context context) {

         super(context);

         init();

       }

     

     /**

      * @param context

      * @param theme

      */

     protected MyColorDialog(Context context, int theme) {

         super(context, theme);

          init();

       }

     

     /**

      * 

      */

     private void init() {      

          final Theme theme = getContext().getTheme();

          final TypedArray attrs = theme.obtainStyledAttributes(new int[] { android.R.attr.tint });

          tint = attrs.getColor(0, NONE);

       }

     

     /* (non-Javadoc)

      * @see android.app.Dialog#show()

      */

     @Override

     public void show() {

          super.show();

          setTint(tint);

       }

     

     /**

      * @param tint

      */

     public void setTint(int tint) {

         this.tint = tint;

         

        if ( tint != NONE ) {

             Iterator<View> vi = iterator(android.R.id.content);

             while ( vi.hasNext() ) {

                tintView(vi.next(), tint);

             }

          }

       }

     

    /**

      * Set the {@link tint} color for the {@link View}

      * 

      * @param v the {@link View} to change the tint
     * @param tint color tint

     */

     private static void tintView(final View v, final int tint) {

          if ( v != null ) {

             final android.graphics.PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;

   

           if ( v instanceof ImageView ) {

                final Drawable d = ((ImageView)v).getDrawable();

                if ( d != null ) {

                   d.mutate().setColorFilter(tint, mode);

                }

             }

             else {

                final Drawable d = v.getBackground();

                if ( d != null ) {

                   d.setColorFilter(tint, mode);

                }

             }

          }

       }

     

 
     /**

      * @param button

      */

     public void setCancelButton(Button button) {

        button.setOnClickListener(new View.OnClickListener() {

 

           public void onClick(View v) {

               cancel();

             }

     

        });

       }

     

     /**

      * @param button

      */

     public void setPositiveButton(Button button) {

          button.setOnClickListener(new View.OnClickListener() {

  

          public void onClick(View v) {

               dismiss();

            }

    

       });

      }

  



    /**

     * Return a {@link ChildrenIterator} starting at the {@link ViewGroup}

     * identified by the specified resource id.

     *  

     * @param res resource id of the {@link ViewGroup} to start the iteration

     * @return iterator

     */

    public Iterator<View> iterator(int res) {

        final ViewGroup vg = (ViewGroup)findViewById(res);

        return new ChildrenIterator<View>(vg);

      }
  



   public static class Builder extends AlertDialog.Builder {

  

       private MyColorDialog dialog;

  

       public Builder(Context context) {

            super(context);

            dialog = new MyColorDialog(context);

         }
       
       @Override
	public Builder setView(View vi)
       {
    	   dialog.setView(vi);
    	   
    	   return this;
    	   
       }

  

       public Builder(Context context, int theme) {

            super(context);

            dialog = new MyColorDialog(context, theme);

         }

        

       @Override

       public MyColorDialog create() {

            return dialog;

         }

  

       @Override

       public Builder setMessage(CharSequence message) {

            dialog.setMessage(message);

            return this;

         }

  

       @Override

       public Builder setTitle(CharSequence title) {

            dialog.setTitle(title);

            return this;

         }

  

     

       /*public Builder setPositiveButton(CharSequence text,

              OnClickListener listener) {

            dialog.setButton(BUTTON_POSITIVE, text, listener);

            return this;

         }

  

          @Override*/

         /* public Builder setIcon(int iconId) {

               dialog.setIcon(iconId);

               return this;

            }*/

  

    }

  }
