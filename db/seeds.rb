150.times do
  Food.create({
    dish: Faker::Food.dish,
    measurement: Faker::Food.measurement,
    description: Faker::Food.description,
    ingredient: Faker::Food.ingredient,
    measurement: Faker::Food.measurement,
    metric_measurement: Faker::Food.metric_measurement,
    spice: Faker::Food.spice,
    vegetables: Faker::Food.vegetables
    })
end
