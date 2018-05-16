package com.example.test;

import java.util.* ;
 
 public class Sieve
 {
     private BitSet sieve ;
 
     public Sieve ( int size ) {
         sieve = new BitSet ( ( size + 1 ) / 2 ) ;
     }
 
     public boolean is_composite ( int k )
     {
         assert k >= 3 && ( k % 2 ) == 1 ;
         return sieve. get ( ( k - 3 ) / 2 ) ;
     }
 
     public void set_composite ( int k )
     {
         assert k >= 3 && ( k % 2 ) == 1 ;
         sieve. set ( ( k - 3 ) / 2 ) ;
     }
 
     public static List < Integer > sieve_of_eratosthenes ( int max )
     {
         Sieve sieve = new Sieve ( max + 1 ) ; // +1 to include max itself
         for ( int i = 3 ; i * i <= max ; i += 2 ) {
             if ( sieve. is_composite ( i ) )
                 continue ;
 
             // We increment by 2*i to skip even multiples of i
             for ( int multiple_i = i * i ; multiple_i <= max ; multiple_i += 2 * i )
                 sieve. set_composite ( multiple_i ) ;
 
         }
         List < Integer > primes = new ArrayList < Integer > ( ) ;
         primes. add ( 2 ) ;
         for ( int i = 3 ; i <= max ; i += 2 )
             if ( ! sieve. is_composite ( i ) )
                 primes. add ( i ) ;
 
         return primes ;
     }
     
     public static int sieve_of_eratosthenes_count ( int max )
     {
         Sieve sieve = new Sieve ( max + 1 ) ; // +1 to include max itself
         for ( int i = 3 ; i * i <= max ; i += 2 ) {
             if ( sieve. is_composite ( i ) )
                 continue ;
 
             // We increment by 2*i to skip even multiples of i
             for ( int multiple_i = i * i ; multiple_i <= max ; multiple_i += 2 * i )
                 sieve. set_composite ( multiple_i ) ;
 
         }
         int cnt = 1;
         for ( int i = 3 ; i <= max ; i += 2 )
             if ( ! sieve. is_composite ( i ) )
                 cnt++ ;
 
         return cnt ;
     }
 }