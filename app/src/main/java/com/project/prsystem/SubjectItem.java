package com.project.prsystem;

/**
 * Created by skplanet on 2016-01-20.
 */
public class SubjectItem {

        public String name;
        public String code;
//       public Bitmap image;
        public String image;
//    public SubjectItem(Bitmap image, String code, String name) {
//        this.image = image;
//        this.code = code;
//        this.name = name;
//    }
        public SubjectItem(String image, String code, String name) {
            this.image = image;
            this.code = code;
            this.name = name;
        }

    @Override
    public String toString() {
        return super.toString();
    }
}

