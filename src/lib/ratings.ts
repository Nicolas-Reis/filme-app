import type { Review } from '@/types';

/**
 * Average rating per movie id, computed client-side from the review list
 * (the backend does not store an aggregate).
 */
export function averageRatingByMovie(reviews: Review[]): Record<number, number> {
  const totals: Record<number, { sum: number; count: number }> = {};

  for (const review of reviews) {
    const id = review.filme.id;
    const entry = totals[id] ?? { sum: 0, count: 0 };
    entry.sum += review.nota;
    entry.count += 1;
    totals[id] = entry;
  }

  const averages: Record<number, number> = {};
  for (const [id, { sum, count }] of Object.entries(totals)) {
    averages[Number(id)] = sum / count;
  }
  return averages;
}
