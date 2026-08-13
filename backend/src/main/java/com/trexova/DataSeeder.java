package com.trexova;

import com.trexova.model.Retreat;
import com.trexova.repository.RetreatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Runs once on startup, seeds sample data if DB is empty
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RetreatRepository retreatRepository;

    @Override
    public void run(String... args) {
        if (retreatRepository.count() > 0) return; // skip if already seeded

        Retreat r1 = new Retreat();
        r1.setTitle("Bali Serenity Yoga Retreat");
        r1.setLocation("Ubud, Bali, Indonesia");
        r1.setCountry("Indonesia");
        r1.setType("yoga");
        r1.setDescription("Immerse yourself in Bali's sacred heart with twice-daily yoga, morning meditation at sunrise, and Balinese healing ceremonies. Nestled among ancient rice terraces.");
        r1.setPrice(1850.0);
        r1.setDuration("7 nights");
        r1.setImageUrl("https://images.unsplash.com/photo-1537953773345-d172ccf13cf1?w=800");
        r1.setRating(4.9);
        r1.setReviewCount(142);
        r1.setFeatured(true);
        r1.setActive(true);
        r1.setAmenities("Twice-daily yoga,Morning meditation,Balinese healing ceremony,Farm-to-table meals,Infinity pool,Airport transfers");
        r1.setAvailableDates("2025-06-07,2025-07-05,2025-08-02");
        retreatRepository.save(r1);

        Retreat r2 = new Retreat();
        r2.setTitle("Tuscany Detox & Renewal");
        r2.setLocation("Val d'Orcia, Tuscany, Italy");
        r2.setCountry("Italy");
        r2.setType("detox");
        r2.setDescription("A luxurious detox journey in a 16th-century Tuscan farmhouse. Cold-pressed juices, infrared saunas, digital detox, and daily walks through vineyards.");
        r2.setPrice(3200.0);
        r2.setDuration("5 nights");
        r2.setImageUrl("https://images.unsplash.com/photo-1523531294919-4bcd7c65e216?w=800");
        r2.setRating(4.8);
        r2.setReviewCount(89);
        r2.setFeatured(false);
        r2.setActive(true);
        r2.setAmenities("Cold-pressed juice cleanse,Infrared sauna,Digital detox,Vineyard walks,Private chef,Lymphatic massage");
        r2.setAvailableDates("2025-06-14,2025-07-19,2025-09-06");
        retreatRepository.save(r2);

        Retreat r3 = new Retreat();
        r3.setTitle("Kerala Ayurveda & Healing");
        r3.setLocation("Varkala, Kerala, India");
        r3.setCountry("India");
        r3.setType("ayurveda");
        r3.setDescription("Authentic Panchakarma detoxification under certified Ayurvedic doctors. Daily treatments, herbal medicines, and personalized diet plans in a traditional Kerala setting.");
        r3.setPrice(980.0);
        r3.setDuration("14 nights");
        r3.setImageUrl("https://images.unsplash.com/photo-1600334129128-685c5582fd35?w=800");
        r3.setRating(4.9);
        r3.setReviewCount(203);
        r3.setFeatured(true);
        r3.setActive(true);
        r3.setAmenities("Panchakarma treatments,Doctor consultations,Herbal steam baths,Ayurvedic diet,Pranayama yoga,Meditation");
        r3.setAvailableDates("2025-06-01,2025-06-15,2025-07-01");
        retreatRepository.save(r3);

        Retreat r4 = new Retreat();
        r4.setTitle("Tulum Sound Healing Journey");
        r4.setLocation("Tulum, Quintana Roo, Mexico");
        r4.setCountry("Mexico");
        r4.setType("meditation");
        r4.setDescription("Sound baths, cacao ceremonies, cenote swims, and guided breathwork in the heart of the Mayan Riviera. Transformative and deeply restorative.");
        r4.setPrice(2100.0);
        r4.setDuration("6 nights");
        r4.setImageUrl("https://images.unsplash.com/photo-1552083974-186346191183?w=800");
        r4.setRating(4.7);
        r4.setReviewCount(67);
        r4.setFeatured(false);
        r4.setActive(true);
        r4.setAmenities("Daily sound baths,Cacao ceremonies,Cenote swimming,Breathwork,Kundalini yoga,Plant-based meals");
        r4.setAvailableDates("2025-05-25,2025-06-22,2025-07-27");
        retreatRepository.save(r4);

        Retreat r5 = new Retreat();
        r5.setTitle("Portugal Silent Meditation");
        r5.setLocation("Alentejo, Portugal");
        r5.setCountry("Portugal");
        r5.setType("silent");
        r5.setDescription("Noble silence, Vipassana meditation, and complete digital disconnection in a converted cork oak farmhouse guided by experienced teachers.");
        r5.setPrice(1400.0);
        r5.setDuration("10 nights");
        r5.setImageUrl("https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800");
        r5.setRating(4.8);
        r5.setReviewCount(51);
        r5.setFeatured(false);
        r5.setActive(true);
        r5.setAmenities("Noble silence,Vipassana meditation,Digital detox,Vegetarian meals,Walking meditation,Teacher sessions");
        r5.setAvailableDates("2025-06-03,2025-08-10,2025-09-14");
        retreatRepository.save(r5);

        System.out.println("✅ 5 sample retreats seeded.");
    }
}