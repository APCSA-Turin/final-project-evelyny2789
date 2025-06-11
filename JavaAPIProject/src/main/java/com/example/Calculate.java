// package com.example;

// import java.util.ArrayList;

// class Calculate {
//     public Calculate(ArrayList arrayList) {
//         int least = 0;
//         ArrayList<String> ordered = new ArrayList<>();
//         while (arrayList.size() > 0) {
//             int ind = 0;
//             for (int i = 1; i < arrayList.size(); i++) {
//                 if (arrayList.get(i).calories < arrayList.get(least).calories) {
//                     least = i;
//                 }
//                 ind = i;
//             }
//             ordered.add(arrayList.get(ind).toString());
//             arrayList.remove(ind);
//         }
//     }
// }
